import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import FormLabel from '@material-ui/core/FormLabel';
import FormControl from '@material-ui/core/FormControl';
import FormGroup from '@material-ui/core/FormGroup';
import FormControlLabel from '@material-ui/core/FormControlLabel';
import FormHelperText from '@material-ui/core/FormHelperText';
import Checkbox from '@material-ui/core/Checkbox';
import classes from './DaysCheckBox.module.css'

const mapIndexToDayString = ((index) => {
    const DAYS = ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday"]
    return DAYS[index];
})

const useStyles = makeStyles((theme) => ({
    root: {
        display: 'flex',
    },
    formControl: {
        margin: theme.spacing(3),
    },
}));

export default function CheckboxesGroup() {
    const classes = useStyles();
    const [userRestrictions, setRestrictions] = React.useState([
        true,
        false,
        false,
        false,
        false
    ]);

    const handleChange = (event) => {
        const newRestrictions = [...userRestrictions];
        newRestrictions[event.target.name] = event.target.checked;
        setRestrictions(newRestrictions);
    };

    return (
        <div className={classes.root}>
            <FormControl component="fieldset" className={classes.formControl}>
                <FormLabel component="legend">Pick days for your worker</FormLabel>
                <FormGroup>
                    {userRestrictions.map((canCome, i) => 
                        (<FormControlLabel control={<Checkbox checked={canCome} onChange={handleChange} name={i} />} label={mapIndexToDayString(i)}/>))}
                </FormGroup>
            </FormControl>
        </div>
    );
}