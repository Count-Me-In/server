import React, { useState } from 'react';
import { createMuiTheme, ThemeProvider } from "@material-ui/core/styles";
import classes from './App.module.css';
import Login from './components/Login/Login'
import Faculty from './components/Faculty/Faculty'
import AppBar from "@material-ui/core/AppBar";
import { Toolbar, Popper, Grow, ClickAwayListener, MenuItem, Paper, MenuList } from "@material-ui/core";
import {
  BrowserRouter as Router,
  Switch,
  Route,
  Redirect,
  Link
} from "react-router-dom";
import HomePage from './components/HomePage/HomePage';
import Bidding from './components/Bidding/Bidding';
import TextField from '@material-ui/core/TextField';
import Button from '@material-ui/core/Button';
import Popover from '@material-ui/core/Popover';
import Typography from '@material-ui/core/Typography';
import PlanArrival from './components/PlanArrival/PlanArrival';
import ManageEmployeePoints from './components/ManageEmployeePoints/ManageEmployeePoints';
import Restrictions from './components/Restrictions/Restrictions';

const theme = createMuiTheme({
  typography: {
    fontFamily: "Segoe UI",
  },
  palette: {
    primary: {
      light: "#6abba7",
      main: "#ff9800",
      dark: "#307766",
      contrastText: "#fff",
    },
    secondary: {
      light: "#ffffff",
      main: "#a8abab",
      dark: "#b2b2b2",
      contrastText: "#000",
    },
  },
});


function App() {
  const [loggedUser, setUser] = useState({name: 'Itay', isManager: true});
  const [tab, setTab] = useState();
  const [openMenu, setOpen] = React.useState(false);
  const [currP, setCurrP] = useState();//TODO: ask from server
  const [anchorEl, setAnchorEl] = React.useState(null);
  const isLoggedIn = Object.keys(loggedUser).length != 0;
  const anchorRef = React.useRef(null);


  const handleToggleMenu = () => {
    setOpen((prevOpen) => !prevOpen);
  };

  const handleCloseMenu = (event) => {
    if (anchorRef.current && anchorRef.current.contains(event.target)) {
      return;
    }

    setOpen(false);
  };

  const handleMenuSelection = (event) => {
    handleCloseMenu(event);
    // setTab(3);
  }
  const handleCloseInfo = () => {
    setAnchorEl(null);
  };

  const open = Boolean(anchorEl);
  const id = open ? 'simple-popover' : undefined;

  const handleClick = (event) => {
    setAnchorEl(event.currentTarget);
  };

  function handleListKeyDown(event) {
    if (event.key === 'Tab') {
      event.preventDefault();
      setOpen(false);
    }
  }

  return (
    <ThemeProvider theme={theme}>

      <Router>
        <AppBar position="static">
          <Toolbar className={classes.navbar}>
            <div className={classes.titleContainer}>
              <label className={classes.mainTitle}><strong>Count Me In The Office!</strong></label>
            </div>
            {!isLoggedIn &&
              <div>
                <Button className={classes.Info} aria-describedby={id} variant="contained" color="primary" onClick={handleClick}>
                  info
                </Button>
                <Popover
                  id={id}
                  open={open}
                  anchorEl={anchorEl}
                  onClose={handleCloseInfo}
                  anchorOrigin={{
                    vertical: 'bottom',
                    horizontal: 'center',
                  }}
                  transformOrigin={{
                    vertical: 'top',
                    horizontal: 'center',
                  }}
                >
                  <Typography className={classes.typography}>
                    Welcome to Count Me In The Office!
    באתר זה תוכלו להרשם להרצאות המתקיימות הסמסטר בצורה היברידית והמערכת תחשב ותבחר מי יורשה להכנס לשיעור הפרונטלי בצורה הוגנת. הינך מקבל סך נקודות שבועיות אשר מצטברות משבוע לשבוע. עלייך לחלק את הנקודות שברשותך בין הקורסים הנמצאים במערכת השעות שלך. החלוקה תעשה בעזרת חלוקת אחוזים. ברשותך 100% אותם תוכל לחלק בין הקורסים השונים. שים לב כי החלוקה נתונה לשיקול דעתך, אינך חייב לחלק את כל האחוזים ואינך חייב לחלק אחוזים לכל קורס. לאחר מכן המערכת תשקלל את בחירות כל התלמידים ותבחר מי ייכנס לכל שיעור. את חלוקת האחוזים תוכל לשנות באופן דינאמי בלשונית biding.
    את מערכת השעות והשיבוצים לשבועיים הקרובים תוכל לראות בלשונית schedule כאשר השיעורים אליהם הצלחת להיכנס יסומנו בירוק, ואלה שלא יסומנו באפור. שים לב שכל חלוקת אחוזים רלוונטית לשבוע השלישי.</Typography>
                </Popover>
              </div>
            }
            {isLoggedIn &&
              <div className={classes.navbarChild}>
                <div className={classes.biddindData}>
                  <label><strong>points: 1500</strong></label>
                  <label><strong>Percents: {currP}%</strong></label>
                </div>
                <div className={classes.tabs}>
                  <Link to="/Home" onClick={() => setTab(1)} className={!isLoggedIn || tab != 1 ? classes.link : classes.clickedLink}>
                    <h3>Schedule</h3>
                  </Link>
                  <Link to="/bidding" onClick={() => setTab(2)} className={!isLoggedIn || tab != 2 ? classes.link : classes.clickedLink} >
                    <h3>Bidding</h3>
                  </Link>
                  {loggedUser.isManager &&
                    <div className={classes.dropMenu}>
                      <Button
                        className={!isLoggedIn || tab != 3 ? classes.link : classes.clickedLink}
                        ref={anchorRef}
                        aria-controls={open ? 'menu-list-grow' : undefined}
                        aria-haspopup="true"
                        onClick={handleToggleMenu}
                      >
                        Manager Panel
                      </Button>
                      <Popper className={classes.MenuList} open={openMenu} anchorEl={anchorRef.current} role={undefined} transition disablePortal>
                        {({ TransitionProps, placement }) => (
                          <Grow
                            {...TransitionProps}
                            style={{ transformOrigin: placement === 'bottom' ? 'center top' : 'center bottom' }}
                          >
                            <Paper>
                              <ClickAwayListener onClickAway={handleCloseMenu}>
                                <MenuList autoFocusItem={openMenu} id="menu-list-grow" onKeyDown={handleListKeyDown}>
                                  <MenuItem onClick={handleMenuSelection}>
                                    <Link to="/employeesPoints" onClick={() => setTab(3)}  >
                                      Employees points
                                    </Link>
                                  </MenuItem>
                                  <MenuItem onClick={handleMenuSelection}>
                                    <Link to="/restriction" onClick={() => setTab(3)}  >
                                      Days restriction
                                    </Link>
                                  </MenuItem>
                                  <MenuItem onClick={handleMenuSelection}>
                                    <Link to="/arrival" onClick={() => setTab(3)}  >
                                      Plan arrival
                                    </Link>
                                  </MenuItem>
                                </MenuList>
                              </ClickAwayListener>
                            </Paper>
                          </Grow>
                        )}
                      </Popper>
                    </div>
                    }
                </div>
                    <Link onClick={() => { setUser({}) }} to="/" className={classes.logout}>
                      <h3>log out</h3>
                    </Link>
              </div>
                }
          </Toolbar>
        </AppBar >
              <Switch>
                <PrivateRoute loggedUser={loggedUser} path="/home">
                  <HomePage loggedUser={loggedUser} />
                </PrivateRoute>
                <PrivateRoute loggedUser={loggedUser} path="/bidding">
                  <Bidding updatePercents={(p) => { setCurrP(p) }} />
                </PrivateRoute>
                <Route path="/login">
                  <Login onLogin={(user) => {
                    setTab(1)
                    setUser(user)
                  }} />
                </Route>
                
                <PrivateRoute loggedUser={loggedUser} isManager={true} path="/employeesPoints">
                  <ManageEmployeePoints />
                </PrivateRoute>
                
                <PrivateRoute loggedUser={loggedUser} isManager={true} path="/restriction">
                  <Restrictions />
                </PrivateRoute>
                
                <PrivateRoute loggedUser={loggedUser} isManager={true} path="/arrival">
                  <PlanArrival />
                </PrivateRoute>
                <Route path="/">
                  <Redirect
                    to={{
                      pathname: "/login",
                    }}
                  />
                  <Login />
                </Route>
              </Switch>
      </Router>
    </ThemeProvider >
          );
        }
        
function PrivateRoute({children, loggedUser, isManager}) {
  return (
    <Route
            render={({ location }) =>
              Object.keys(loggedUser).length === 0 ? 
              (
                <Redirect
                  to={{
                    pathname: "/login",
                    state: { from: location }
                  }}
                />
              ) : loggedUser.isManager || !isManager ?
              ( children ) :
              (
                <Redirect
                  to={{
                    pathname: "/home",
                  }}
                />
              )
            }
          />
          );
        }
        
        export default App;
