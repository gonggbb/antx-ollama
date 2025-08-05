// components/ConversationMenu.js
import React from 'react';
import { Button } from 'antd';
import { PlusOutlined } from '@ant-design/icons';
import { Conversations } from '@ant-design/x';
const ConversationMenu = ({ conversationsItems, activeKey, onAddConversation, onConversationClick, styles }) => {
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
    <div className={styles.menu}>
      {logoNode}
      <Button onClick={onAddConversation} type="link" className={styles.addBtn} icon={<PlusOutlined />}>
        New Conversation
      </Button>
      <Conversations
        items={conversationsItems}
        className={styles.conversations}
        activeKey={activeKey}
        onActiveChange={onConversationClick}
      />
    </div>
  );
}

export default ConversationMenu;