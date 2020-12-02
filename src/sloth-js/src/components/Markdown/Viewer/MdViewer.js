import React from 'react';
import ReactMarkdown from 'react-markdown'
import gfm from 'remark-gfm'


export default function MdViewer({ content }) {
  return (
    <div className='markdown-body' >
        <ReactMarkdown plugins={[gfm]} children={content} />
    </div>
  );
}