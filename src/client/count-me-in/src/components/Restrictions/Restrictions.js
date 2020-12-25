import React, { useState, Text} from 'react';
import classes from './Restrictions.module.css'
import { TextField} from '@material-ui/core';
import EmployeeCheckbox from '../EmployeeDayList/EmployeeDayList';

function Restrictions() {

    const employees = [
        {
            name: "Shenhav",
        },
        {
            name: "Noy",
        },
        {
            name: "Nufar",
        },
        {
            name: "Shauli",
        }
    ];

    return (
        <div>
            <h1 className={classes.header}>Day Restrictions</h1>
            <div className={classes.empBox}>
                {employees.map((emp) => <EmployeeCheckbox name={emp.name}></EmployeeCheckbox>)}
            </div>
            
        </div>)
}

export default Restrictions;