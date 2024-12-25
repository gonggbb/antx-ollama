import { Attachments, Bubble, Conversations, Prompts, Sender, Welcome, useXAgent, useXChat } from '@ant-design/x'; 
import React, { useEffect } from 'react';
import {
  CloudUploadOutlined, 
  EllipsisOutlined,
  PaperClipOutlined,
  PlusOutlined,
  ShareAltOutlined,
  UserOutlined,
} from '@ant-design/icons';
import { Badge, Button, Space } from 'antd';

import { useStyle } from "./style"
import { useChatLogic } from './hooks/useChatLogic';
import { defaultConversationsItems, senderPromptsItems, placeholderPromptsItems, roles } from "./mock"

const Independent = () => {
  // ==================== Style ====================
  const { styles } = useStyle();

  // ==================== State ====================
  const {
    headerOpen,
    setHeaderOpen,
    content,
    setContent,
    conversationsItems,
    setConversationsItems,
    activeKey,
    setActiveKey,
    attachedFiles,
    setAttachedFiles,
    messagesMap,
    setMessagesMap,
  } = useChatLogic(defaultConversationsItems);

  const [agent] = useXAgent({
    request: async ({ message }, { onSuccess, onError }) => {
      try {
        console.log('Sending message:', message);

        const response = await fetch('http://localhost:11434/api/chat', {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify({
            messages: [{ role: 'user', content: message }],
            model: 'llama3.1',
          }),
        });

        if (!response.ok) throw new Error('Network response was not ok');

        const reader = response.body?.getReader();
        const decoder = new TextDecoder('utf-8');
        let done = false;
        let fullMessage = '';
        let newKey = Date.now();

        setMessagesMap(prev => ({
          ...prev,
          [activeKey]: [
            ...(prev[activeKey] || []),
            { id: newKey, content: '', role: 'ai', typing: true, loading: true }
          ],
        }));

        while (!done) {
          const { value, done: streamDone } = (await reader?.read()) || { done: true };
          done = streamDone;

          if (value && !done) {
            const chunk = decoder.decode(value, { stream: !done });
            const lines = chunk.split('\n').filter(line => line.trim());

            for (const line of lines) {
              try {
                const data = JSON.parse(line);
                if (data.message?.content) {
                  fullMessage += data.message.content;
                  setMessagesMap(prev => ({
                    ...prev,
                    [activeKey]: prev[activeKey].map(msg =>
                      msg.id === newKey ? { ...msg, typing: true, content: fullMessage, loading: false } : msg
                    ),
                  }));
                }
              } catch (parseError) {
                console.error('Failed to parse JSON:', parseError);
              }
            }
          }
          if (done) {
            console.log('data.done>>>>>onSuccess message:', fullMessage);
            onSuccess(fullMessage);
            setMessagesMap(prev => ({
              ...prev,
              [activeKey]: prev[activeKey].map(msg =>
                msg.id === newKey ? { ...msg, typing: false, content: fullMessage } : msg
              ),
            }));
            setConversationsItems(prev => {
              let item = prev.find(conver => conver.key === activeKey);
              if (item && item.init) {
                item.init = false;
                item.label = fullMessage.substring(0, 20);
              }
              return prev;
            });
            return;
          }
        }
      } catch (error) {
        console.error('Request error:', error);
        onError(error);
      }
    }
  });

  const { onRequest, messages, setMessages } = useXChat({
    agent,
    onSuccess: (response) => {
      console.log('useXChat:onSuccess', response);
    },
  });

  useEffect(() => {
    console.log('useEffect activeKey changed:', activeKey);
  }, [activeKey]);

  useEffect(() => {
    console.log('useEffect messagesMap  changed:', messagesMap);
  }, [messagesMap]);

  const items = (messagesMap[activeKey] || []).map(({ id, loading, content, status, role }) => ({
    key: id,
    loading: loading,
    role: role,
    content: content,
  }));

  const onSubmit = (nextContent) => {
    if (!nextContent) return;

    console.log('Submitting message:', nextContent);

    const newKey = Date.now();
    onRequest(nextContent);
    setMessagesMap(prev => {
      const newMessages = [
        ...(prev[activeKey] || []),
        { id: newKey, content: nextContent, role: 'local', },
      ];
      return {
        ...prev,
        [activeKey]: newMessages,
      };
    });
    setContent('');
  };

  const onPromptsItemClick = (info) => {
    console.log('Prompt item clicked:', info);
    onRequest(info.data.description);
  };

  const onAddConversation = () => {
    console.log('新增对话 Adding new conversation');
    setConversationsItems([
      ...conversationsItems,
      {
        init:true,
        key: `${conversationsItems.length}`,
        label: `New Conversation ${conversationsItems.length}`,
      },
    ]);
    setActiveKey(`${conversationsItems.length}`);
  };

  const onConversationClick = (key) => {
    console.log('切换对话 Conversation clicked:', key);
    setActiveKey(key);

    if (!messagesMap[key]) {
      setMessagesMap((prev) => ({
        ...prev,
        [key]: [],
      }));
    }
  };

  const handleFileChange = (info) => {
    console.log('File changed:', info);
    setAttachedFiles(info.fileList);
  };

  const placeholderNode = (
    <Space direction='vertical' size={16} className={styles.placeholder}>
      <Welcome
        variant='borderless'
        icon='https://mdn.alipayobjects.com/huamei_iwk9zp/afts/img/A*s5sNRo5LjfQAAAAAAAAAAAAADgCCAQ/fmt.webp'
        title="Hello, I'm AntX-O"
        description='Base on Ant Design, AGI product interface solution, create a better intelligent vision~'
        extra={
          <Space>
            <Button icon={<ShareAltOutlined />} />
            <Button icon={<EllipsisOutlined />} />
          </Space>
        }
      />
      <Prompts
        title='Do you want?'
        items={placeholderPromptsItems}
        styles={{
          list: {
            width: '100%',
          },
          item: {
            flex: 1,
          },
        }}
        onItemClick={onPromptsItemClick}
      />
    </Space>
  );

  const attachmentsNode = (
    <Badge dot={attachedFiles.length > 0 && !headerOpen}>
      <Button type='text' icon={<PaperClipOutlined />} onClick={() => setHeaderOpen(!headerOpen)} />
    </Badge>
  );

  const senderHeader = (
    <Sender.Header
      title='Attachments'
      open={headerOpen}
      onOpenChange={setHeaderOpen}
      styles={{
        content: {
          padding: 0,
        },
      }}
    >
      <Attachments
        beforeUpload={() => false}
        items={attachedFiles}
        onChange={handleFileChange}
        placeholder={type =>
          type === 'drop'
            ? { title: 'Drop file here' }
            : {
              icon: <CloudUploadOutlined />,
              title: 'Upload files',
              description: 'Click or drag files to this area to upload',
            }
        }
      />
    </Sender.Header>
  );

  const logoNode = (
    <div className={styles.logo}>
      <img
        src='https://mdn.alipayobjects.com/huamei_iwk9zp/afts/img/A*eco6RrQhxbMAAAAAAAAAAAAADgCCAQ/original'
        draggable={false}
        alt='logo'
      />
      <span>AntX-O</span>
    </div>
  );

  return (
    <div className={styles.layout}>
      <div className={styles.menu}>
        {logoNode}
        <Button onClick={onAddConversation} type='link' className={styles.addBtn} icon={<PlusOutlined />}>
          New Conversation
        </Button>
        <Conversations
          items={conversationsItems}
          className={styles.conversations}
          activeKey={activeKey}
          onActiveChange={onConversationClick}
        />
      </div>
      <div className={styles.chat}>
        <Bubble.List
          items={items.length > 0 ? items : [{ content: placeholderNode, variant: 'borderless' }]}
          roles={roles}
          className={styles.messages}
        />
        <Prompts items={senderPromptsItems} onItemClick={onPromptsItemClick} className={styles.Prompts} />
        <Sender
          value={content}
          header={senderHeader}
          onSubmit={onSubmit}
          onChange={setContent}
          prefix={attachmentsNode}
          loading={agent.isRequesting()}
          className={styles.sender}
        />
      </div>
    </div>
  );
};

export default Independent;
