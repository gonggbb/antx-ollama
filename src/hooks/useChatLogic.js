// hooks/useChatLogic.js
import { useState, useEffect } from 'react';

export const useChatLogic = (defaultConversationsItems) => {
  const [headerOpen, setHeaderOpen] = useState(false);
  const [content, setContent] = useState('');
  const [conversationsItems, setConversationsItems] = useState(defaultConversationsItems);
  const [activeKey, setActiveKey] = useState(defaultConversationsItems[0].key);
  const [attachedFiles, setAttachedFiles] = useState([]);
  const [messagesMap, setMessagesMap] = useState({
    [defaultConversationsItems[0].key]: [],
  });

  useEffect(() => {
    console.log('activeKey changed:', activeKey);
    console.log('content changed:', content);
  }, [activeKey, content]);

  useEffect(() => {
    console.log('Messages map changed:', messagesMap);
  }, [messagesMap]);

  useEffect(() => {
    console.log('conversationsItems  changed:', conversationsItems);
  }, [conversationsItems]);


  return {
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
  };
};
