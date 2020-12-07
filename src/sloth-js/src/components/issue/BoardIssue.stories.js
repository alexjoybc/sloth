import React from 'react';

import Issue from './BoardIssue';

export default {
    component: Issue,
    title: 'Issue',
  };

const Template = args => <Issue {...args} />;

export const Default = Template.bind({});
Default.args = {
  title: 'test',
  text: 'some text' 
};