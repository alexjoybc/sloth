import React from 'react';

import Column from './Column';

export default {
    component: Column,
    title: 'Column',
  };

const Template = args => <Column {...args} />;

export const Default = Template.bind({});
Default.args = {
    title: 'test',
    issues: [ {
        title: "test"
    }, {
        title: "test2"
    }],
    onAddIssue: function() { alert('test'); }
};
