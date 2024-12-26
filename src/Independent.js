// Independent.js
import React from 'react';
import { useXChat, useXAgent } from '@ant-design/x';
import { useChatLogic } from './hooks/useChatLogic';
import { addMessageToMap } from './services/messageService';
import ConversationMenu from './components/ConversationMenu';
import ChatBubbleList from './components/ChatBubbleList';
import PromptList from './components/PromptList';
import MySender from './components/MySender';
import { defaultConversationsItems, senderPromptsItems, roles } from './mock';
import { useStyle } from './style';

const Independent = () => {
  const { styles } = useStyle();
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

  /* 它处理发送用户消息，并通过流式读取响应体逐步接收并解析返回的数据 */
  const [agent] = useXAgent({
    request: async ({ message }, { onSuccess, onError }) => {
      try {
        console.log('Sending message:', message); // Log message being sent

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
  const { onRequest, } = useXChat({
    agent,
    onSuccess: (response) => {
      console.log('useXChat:onSuccess', response);
    },
  });

  const onSubmit = (nextContent) => {
    if (!nextContent) return;
    console.log('Submitting message:', nextContent); // Log message submission
    onRequest(nextContent);
    setMessagesMap(prev => addMessageToMap(prev, activeKey, { id: Date.now(), content: nextContent, role: 'local' }));
    // 延迟清空 content，确保 setMessagesMap 执行后
    setTimeout(() => setContent(''), 0);
  };

  const onAddConversation = () => {
    const newKey = `${conversationsItems.length}`;
    setConversationsItems([...conversationsItems, { key: newKey, init: true, label: `New Conversation ${conversationsItems.length}` }]);
    setActiveKey(newKey);
  };

  const onConversationClick = (key) => {
    setActiveKey(key);
    if (!messagesMap[key]) {
      setMessagesMap(prev => ({ ...prev, [key]: [] }));
    }
  };

  const onPromptsItemClick = (info) => {
    onSubmit(info.data.description);
  };

  const handleFileChange = (info) => {
    setAttachedFiles(info.fileList);
  };


  const items = (messagesMap[activeKey] || []).map(({ id, loading, content, role }) => ({
    key: id,
    loading,
    role,
    content,
  }));


  return (
    <div className={styles.layout}>
      <ConversationMenu
        conversationsItems={conversationsItems}
        activeKey={activeKey}
        onAddConversation={onAddConversation}
        onConversationClick={onConversationClick}
        styles={styles}
      />
      <div className={styles.chat}>
        <ChatBubbleList items={items} roles={roles} styles={styles} />
        <PromptList items={senderPromptsItems} onItemClick={onPromptsItemClick} styles={styles} />
        <MySender
          attachedFiles={attachedFiles}
          handleFileChange={handleFileChange}
          value={content}
          onSubmit={onSubmit}
          agent={agent}
          headerOpen={headerOpen}
          setHeaderOpen={setHeaderOpen}
          onChange={setContent}
          styles={styles}
        />
      </div>
    </div>
  );
};

export default Independent;
