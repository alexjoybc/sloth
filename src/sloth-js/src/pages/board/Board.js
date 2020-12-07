import React from 'react';
import PropTypes from 'prop-types';
import Column from '../../components/column/Column'


export default function Board({ title }) {

    const data = [{
        title: "to do",
        issues: [
            { title: "first" },
            { title: "second" }]
    }, {
        title: "in progress",
        issues: [
            { title: "an item" }
        ]
    }];

    return (

        <div className={"container-fluid"}>
            <div className={"row"}>
                {data.map(column =>
                    <div className={"col-2"}><Column {...column} /></div>)}
            </div>
        </div>);
}

Board.propTypes = {
    title: PropTypes.string.isRequired
}
