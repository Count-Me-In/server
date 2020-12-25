import React, { useState, Text} from 'react';
import classes from './ManageEmployeePoints.module.css'
import { TextField} from '@material-ui/core';
import EmployeePoints from './EmployeePoints/EmployeePoints';

function ManageEmployeePoints() {
    const [totalPoints, setTotalPoints] = useState(250);

    const employees = [
        {
            name: "Shenhav",
            points: 40
        },
        {
            name: "Noy",
            points: 50
        },
        {
            name: "Nufar",
            points: 100
        },
        {
            name: "Shauli",
            points: 10
        }
    ];


    return (
        <div>
            <h1 className={classes.header}>Manage Employees Points</h1>
            <div className={classes.empBox}>
                {employees.map((emp) => <EmployeePoints name={emp.name} points={emp.points}></EmployeePoints>)}
            </div>  
            <h1 className={classes.totalPoints}>Total Points: {totalPoints}</h1>
        </div>)
}

export default ManageEmployeePoints;