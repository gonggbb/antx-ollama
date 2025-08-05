// components/ChatBubbleList.js
import React from 'react';
import { Bubble, Prompts, Welcome, } from '@ant-design/x';

import {
  EllipsisOutlined,
  ShareAltOutlined,
} from '@ant-design/icons';
import { Button, Space } from 'antd';
import { placeholderPromptsItems, } from '../mock';

const ChatBubbleList = ({ items, roles, styles, onPromptsItemClick }) => {
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
  return (
    <Bubble.List
      items={items.length > 0 ? items : [{ content: placeholderNode, variant: 'borderless' }]}
      roles={roles}
      className={styles.messages}
    />
  );
}

export default ChatBubbleList;