import React from 'react';

import MdViewer from './MdViewer';

export default {
  component: MdViewer,
  title: 'MdViewer',
};

const Template = args => <MdViewer {...args} />;

export const Default = Template.bind({});
Default.args = {
  content: `A paragraph with *emphasis* and **strong importance**.

  > A block quote with ~strikethrough~ and a URL: https://reactjs.org.
  
  * Lists
  * [ ] todo
  * [x] done
  
  A table:
  
  | a | b |
  | - | - |
  `
};