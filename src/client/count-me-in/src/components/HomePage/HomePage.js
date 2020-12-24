import React, { useState } from 'react';
import classes from './HomePage.module.css';
import Paper from '@material-ui/core/Paper';
import TextField from '@material-ui/core/TextField';
import { ViewState, EditingState, IntegratedEditing } from '@devexpress/dx-react-scheduler';
import {
    Scheduler,
    MonthView,
    Appointments,
    AppointmentTooltip,
    Toolbar,
    DateNavigator,
    TodayButton,
    Resources,
} from '@devexpress/dx-react-scheduler-material-ui';
import { teal, grey } from '@material-ui/core/colors';
import { Button } from '@material-ui/core';
import { Autocomplete } from '@material-ui/lab';



const schedulerData = [
    { startDate: '2020-12-24T09:45', endDate: '2020-12-24T10:45', isIn: 0 },
    { startDate: '2020-12-25T09:45', endDate: '2020-12-25T10:45', isIn: 1 },
    { startDate: '2021-01-01T09:45', endDate: '2021-01-01T10:45', isIn: 1 },
];

const allocations = [
    { text: 'We will see you at the office!', id: 1, color: teal },
    { text: 'Maybe next time', id: 0, color: grey },
];

function HomePage() {
    const [currentDate, setDate] = useState(Date.now);
    const [appointments, setData] = useState(schedulerData);
    const resources = [{
        fieldName: 'isIn',
        title: 'כניסה לשיעור',
        instances: allocations,
    }]

    const Appointment = ({ children }) => {
        return <div dir={'rtl'}>
            {children}
        </div>

    }
    const TimeTableCell = ({ onDoubleClick, ...restProps }) => {
        return <MonthView.TimeTable onDoubleClick={undefined} {...restProps} />;
    };

    const employees = ["Shenhav", "Noy", "Nufar", "Shauli"];
    const [employeeID, setEmployeeID] = useState('');

    return (
        <div >
            <Button className={classes.customActions}> Back to Schedule</Button>
            {<div className={classes.customActions}>
                <Autocomplete
                id="autoComplete"
                options={employees}
                getOptionLabel={(option) => option}
                style={{ width: 300 }}
                renderInput={(params) => <TextField {...params} label="Combo box" variant="outlined" />}
                onChange={(event, value) => {setEmployeeID(value); console.log(value)} }
            />
            </div>}
           
            <Paper dir={'ltr'}>
                <Scheduler
                    data={appointments}
                    height={650}
                >
                    <ViewState
                        currentDate={currentDate}
                        onCurrentDateChange={(date) => setDate(date)}

                    />
                    <MonthView
                        onDoubleClick={undefined}

                    />
                    <Toolbar />
                    
                    <DateNavigator />
                    <TodayButton />
                    <Appointments dir={'rtl'} />
                    <Resources
                        data={resources}
                    />  
                    <AppointmentTooltip
                        showCloseButton
                    />
                </Scheduler>
            </Paper>
        </div>
    )
}

export default HomePage;

