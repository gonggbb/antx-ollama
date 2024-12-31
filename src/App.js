import { Attachments, Bubble, Conversations, Prompts, Sender, Welcome, useXAgent, useXChat } from '@ant-design/x';
import React, { useEffect } from 'react';
import {
  CloudUploadOutlined, 
  EllipsisOutlined,
  PaperClipOutlined,
  PlusOutlined,
  ShareAltOutlined,
} from '@ant-design/icons';
import { Badge, Button, Space } from 'antd';

import { useStyle } from "./style"
import { defaultConversationsItems, senderPromptsItems, placeholderPromptsItems, roles } from "./mock"

const Independent = () => {
  // ==================== Style ====================
  const { styles } = useStyle();

  // ==================== State ====================
  // headerOpen: 控制附件管理器的可见性
  const [headerOpen, setHeaderOpen] = React.useState(false);
  // content: 用户输入的消息文本。
  const [content, setContent] = React.useState('');
  // conversationsItems: 存储所有对话项的信息。
  const [conversationsItems, setConversationsItems] = React.useState(defaultConversationsItems);
  // activeKey: 当前激活的对话标识符
  const [activeKey, setActiveKey] = React.useState(defaultConversationsItems[0].key);
  // attachedFiles: 用户上传或选择的文件列表。
  const [attachedFiles, setAttachedFiles] = React.useState([]);
  // messagesMap: 每个对话的消息记录映射表
  const [messagesMap, setMessagesMap] = React.useState({
    [defaultConversationsItems[0].key]: [], // 每个会话的消息记录
  });
  // ==================== Runtime ====================
  /* 它处理发送用户消息，并通过流式读取响应体逐步接收并解析返回的数据 */
  const [agent] = useXAgent({
    request: async ({ message }, { onSuccess, onError }) => {
      try {
        console.log('Sending message:', message); // Log message being sent

        const response = await fetch('http://localhost:11434/api/chat', {
        // docker
        // const response = await fetch('http://ollama:11434/api/chat', {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify({
            messages: [{ role: 'user', content: message }],
            model: 'llama3.2',
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
                  // eslint-disable-next-line no-loop-func
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
            // eslint-disable-next-line no-loop-func
            setMessagesMap(prev => ({
              ...prev,
              [activeKey]: prev[activeKey].map(msg =>
                msg.id === newKey ? { ...msg, typing: false, content: fullMessage } : msg
              ),
            }));
            /* 更新会话标题 */
            setConversationsItems(
              // eslint-disable-next-line no-loop-func
              prev => {
                let item = prev.find(conver => conver.key === activeKey)
                console.log('setConversationsItems:', item)
                if (item && item.init) {
                  item.init = false
                  item.label = fullMessage.substring(0, 20) // 使用 substring 截取前20个字符
                }
                return prev
              }
            );
            done = true;
            return;
          }
        }
      } catch (error) {
        console.error('Request error:', error);
        onError(error);
      }
    }
  });

  /* useXChat用于管理聊天界面中的消息显示和交互逻辑，它依赖于之前创建的agent来进行实际的消息请求。 */
  const { onRequest, messages, setMessages } = useXChat({
    agent,
    onSuccess: (response) => {
      console.log('useXChat:onSuccess', response); // Log response from agent
    },
  });
  /* 监听activeKey的变化，并在每次变化时打印日志 */
  useEffect(() => {
    console.log('useEffect activeKey changed:', activeKey);
    if (activeKey !== undefined) {
      // setMessages([]);
    }
  }, [activeKey]);

  useEffect(() => {
    console.log('useEffect messagesMap  changed:', messagesMap);
  }, [messagesMap]);

  // ==================== 消息列表转换 ====================
  /* 消息列表转换 */
  const items = (messagesMap[activeKey] || []).map(({ id, loading, content, status, role }) => ({
    key: id,
    loading: loading,
    role: role,
    content: content,
  }));

  // ==================== Event ====================
  /* : 用户提交新消息时触发，更新消息列表并将内容设置为空字符串。 */
  const onSubmit = (nextContent) => {
    if (!nextContent) return;

    console.log('Submitting message:', nextContent); // Log message submission

    onRequest(nextContent);
    setMessagesMap(prev => {
      const newMessages = [
        ...(prev[activeKey] || []),
        { id: Date.now(), content: nextContent, role: 'local', },
      ];
      console.log('Updating messagesMap with new message:', newMessages); // Log message submission
      return {
        ...prev,
        [activeKey]: newMessages,
      };
    });
    setContent('');
  };
  /* 用户点击提示词时触发，向服务器发送相应的预设消息。 */
  const onPromptsItemClick = (info) => {
    console.log('Prompt item clicked:', info); // Log prompt item click
    onRequest(info.data.description);
  };
  /* 添加新的对话条目，并将其设为当前激活的对话。 */
  const onAddConversation = () => {
    console.log('新增对话 Adding new conversation'); // Log conversation addition
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

  /* 切换到不同的对话，并确保该对话有对应的空消息列表 */
  const onConversationClick = (key) => {
    console.log('切换对话 Conversation clicked:', key); // Log conversation click
    setActiveKey(key);

    // 如果当前会话没有消息记录，初始化为空数组
    if (!messagesMap[key]) {
      setMessagesMap((prev) => ({
        ...prev,
        [key]: [],
      }));
    }
  };
  /* 文件上传发生变化时更新状态。 */
  const handleFileChange = (info) => {
    console.log('File changed:', info); // Log file changes
    setAttachedFiles(info.fileList);
  };

  // ==================== Nodes 消息列表组件 ====================
  const placeholderNode = (
    <Space direction='vertical' size={16} className={styles.placeholder}>
      {/* 如占位符欢迎信息 */}
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
  /*  附件管理按钮*/
  const attachmentsNode = (
    <Badge dot={attachedFiles.length > 0 && !headerOpen}>
      <Button type='text' icon={<PaperClipOutlined />} onClick={() => setHeaderOpen(!headerOpen)} />
    </Badge>
  );

  /* 发送者头部、Logo等 */
  const senderHeader = (

    /* 头部 */
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
      {/* Attachment 输入附件 */}
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

  // ==================== Render =================
  return (
    <div className={styles.layout}>
       {/* 🌟左侧栏 */}
      <div className={styles.menu}>
        {/* 🌟 Logo */}
        {logoNode}
        {/* 🌟 添加会话 */}
        <Button onClick={onAddConversation} type='link' className={styles.addBtn} icon={<PlusOutlined />}>
          New Conversation
        </Button>

        {/* 🌟 会话管理 */}
        <Conversations
          items={conversationsItems}
          className={styles.conversations}
          activeKey={activeKey}
          onActiveChange={onConversationClick}
        />
      </div>
      <div className={styles.chat}>
        {/* 🌟 消息列表 */}
        <Bubble.List
          items={items.length > 0 ? items : [{ content: placeholderNode, variant: 'borderless' }]}
          roles={roles}
          className={styles.messages}
        />
        {/* 🌟 提示词 */}

        <Prompts items={senderPromptsItems} onItemClick={onPromptsItemClick} className={styles.Prompts} />
        {/* 🌟 输入框 */}
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
