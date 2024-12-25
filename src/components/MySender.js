// components/PromptList.js
import React from 'react';
import { Button, Badge, } from 'antd';
import { CloudUploadOutlined, PaperClipOutlined, } from '@ant-design/icons';
import { Sender, Attachments } from '@ant-design/x';

const MySender = ({ content, agent, onSubmit, setContent, headerOpen, setHeaderOpen, attachedFiles, handleFileChange, styles }) => {

  const senderHeader = (
    <Sender.Header
      title="Attachments"
      open={headerOpen}
      onOpenChange={setHeaderOpen}
      styles={{ content: { padding: 0 } }}
    >
      <Attachments
        beforeUpload={() => false}
        items={attachedFiles}
        onChange={handleFileChange}
        placeholder={(type) =>
          type === 'drop'
            ? { title: 'Drop file here' }
            : { icon: <CloudUploadOutlined />, title: 'Upload files', description: 'Click or drag files to this area to upload' }
        }
      />
    </Sender.Header>
  );
  const attachmentsNode = (
    <Badge dot={attachedFiles.length > 0 && !headerOpen}>
      <Button type="text" icon={<PaperClipOutlined />} onClick={() => setHeaderOpen(!headerOpen)} />
    </Badge>
  );
  return (
    <Sender
      prefix={attachmentsNode}
      value={content}
      header={senderHeader}
      onSubmit={onSubmit}
      onChange={setContent}
        loading={agent?.isRequesting()}
      className={styles.sender}
    />
  );
}

export default MySender;