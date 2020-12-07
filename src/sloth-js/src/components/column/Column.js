import React from 'react';
import PropTypes from 'prop-types';
import { Dropdown } from 'react-bootstrap';
import { MdMoreHoriz } from "react-icons/md";

import Issue from '../issue/BoardIssue';

import './Column.css';


export default function Column({ title, issues, onAddIssue }) {
    return (
        <div>
            <div className="mx-3 mb-3">
                <h2 className="text-secondary column-title" >{title}
                <div className={"float-right"}>
                    <Dropdown>
                        <Dropdown.Toggle size={"sm"} variant="light" id="dropdown-basic">
                            <MdMoreHoriz />
                        </Dropdown.Toggle>

                        <Dropdown.Menu>
                            <Dropdown.Item onClick={onAddIssue} >Add Issue</Dropdown.Item>
                        </Dropdown.Menu>
                    </Dropdown>
                </div>
                </h2>
            </div>
            <div>
                {issues.map((issue) =>
                    <div className="mx-1 mb-3">
                        <Issue {...issue} ></Issue>
                    </div>)}
            </div>
        </div>
    );
}

Column.propTypes = {
    title: PropTypes.string.isRequired,
    issues: PropTypes.array,
    onAddIssue: PropTypes.func
}
