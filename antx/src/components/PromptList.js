// components/PromptList.js
import React from 'react';
import { Prompts } from '@ant-design/x';

const PromptList = ({ items, onItemClick, styles }) => (
  <Prompts
    items={items}
    className={styles.Prompts}
    onItemClick={onItemClick}
  />
);

export default PromptList;