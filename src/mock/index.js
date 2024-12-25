
import { Space } from 'antd';
import React from 'react';
import {
  CommentOutlined,
  HeartOutlined,
  ReadOutlined,
  SmileOutlined,
  FireOutlined,
  UserOutlined,
  RedditOutlined
} from '@ant-design/icons';



/* ---------- 🎉  图标头像组件----------*/

const renderTitle = (icon, title) => (
  <Space align='start'>
    {icon}
    <span>{title}</span>
  </Space>
);

/* ---------- 🎉 提示词 Prompts-----------*/

export const senderPromptsItems = [
  {
    key: '1',
    description: 'Hot Topics',
    icon: <FireOutlined style={{ color: '#FF4D4F' }} />,
  },
  {
    key: '2',
    description: 'Design Guide',
    icon: <ReadOutlined style={{ color: '#1890FF' }} />,
  },
];

/* ---------- 🎉 Conversations 会话管理-----------*/

export const defaultConversationsItems = [
  {
    key: '0',
    init:false, // 是否初始化设置标题
    label: 'AntX-O?',
  },
];
/* ---------- 🎉 角色配置项 <Bubble.List-----------*/
export const roles = {
  ai: {
    placement: 'start',
    typing: { step: 5, interval: 20 },
    styles: {
      content: {
        borderRadius: 16,
      },
    },
    avatar: {
      icon: <RedditOutlined />,
      style: {
        background: '#1677ff',
      },
    },
  },
  local: {
    placement: 'end',
    variant: 'shadow',
    typing: {
      step: 5,
      interval: 20,
    },
    avatar: {
      icon: <UserOutlined />,
      style: {
        background: '#87d068',
      },
    },
  },
};

/* ---------- 🎉 Prompts 提示集-----------*/

export const placeholderPromptsItems = [
  {
    key: '1',
    label: renderTitle(<FireOutlined style={{ color: '#FF4D4F' }} />, 'Hot Topics'),
    description: 'What are you interested in?',
    children: [
      {
        key: '1-1',
        description: `What's new in X?`,
      },
      {
        key: '1-2',
        description: `What's AGI?`,
      },
      {
        key: '1-3',
        description: `Where is the doc?`,
      },
    ],
  },
  {
    key: '2',
    label: renderTitle(<ReadOutlined style={{ color: '#1890FF' }} />, 'Design Guide'),
    description: 'How to design a good product?',
    children: [
      {
        key: '2-1',
        icon: <HeartOutlined />,
        description: `Know the well`,
      },
      {
        key: '2-2',
        icon: <SmileOutlined />,
        description: `Set the AI role`,
      },
      {
        key: '2-3',
        icon: <CommentOutlined />,
        description: `Express the feeling`,
      },
    ],
  },
];