import React from 'react';
import { Card } from 'react-bootstrap';
import PropTypes from 'prop-types';


export default function Issue({ title }) {
    return (
        <Card>
            <Card.Body>
                <Card.Text>{title}</Card.Text>
            </Card.Body>
        </Card>
    );
}

Issue.propTypes = {
    title: PropTypes.string.isRequired
}
