import React, { useState, useEffect } from "react";
import { Routes, Route, Link } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import "./App.css";

import AuthService from "./services/auth.service";

import Login from "./components/Login";
import Register from "./components/Register";
import Home from "./components/Home";
import Profile from "./components/Profile";
import BoardUser from "./components/BoardUser";
import BoardModerator from "./components/BoardModerator";

import EventBus from "./common/EventBus";
import DeviceManagementPage from "./components/DeviceManagementPage";
import UpdateDevice from "./components/UpdateDevice";
import BoardAdmin from "./components/BoardAdmin";
import SoftwareManagementPage from "./components/SoftwareManagementPage";
import LifecycleEventManagementPage from "./components/LifecycleEventManagementPage";
import UpdateSoftware from "./components/UpdateSoftware";
import UpdateLifecycleEvent from "./components/UpdateLifecycleEvent";
import UserViewDevices from "./components/UserViewDevices";
import UserViewSoftware from "./components/UserViewSoftware";

const App = () => {
  const [showManagementBoard, setShowManagementBoard] = useState(false);
  const [showAdminBoard, setShowAdminBoard] = useState(false);
  const [showUserBoard, setShowUserBoard] = useState(false);
  const [currentUser, setCurrentUser] = useState(undefined);

  useEffect(() => {
    const user = AuthService.getCurrentUser();

    if (user) {
      setShowUserBoard(user.roles.includes("ROLE_USER"));
      setCurrentUser(user);
      setShowManagementBoard(user.roles.includes("ROLE_MANAGEMENT"));
      setShowAdminBoard(user.roles.includes("ROLE_ADMIN"));
    }

    EventBus.on("logout", () => {
      logOut();
    });

    return () => {
      EventBus.remove("logout");
    };
  }, []);

  const logOut = () => {
    AuthService.logout();
    setShowManagementBoard(false);
    setShowAdminBoard(false);
    setCurrentUser(undefined);
  };

  return (
    <div>
      <nav className="navbar navbar-expand navbar-dark bg-dark">
        <Link to={"/"} className="navbar-brand">
          bezKoder
        </Link>
        <div className="navbar-nav mr-auto">
          <li className="nav-item">
            <Link to={"/home"} className="nav-link">
              Home
            </Link>
          </li>

          {showManagementBoard && (
            <li className="nav-item">
              <Link to={"/mod"} className="nav-link">
                Management Board
              </Link>
            </li>
          )}

          {showAdminBoard && (
            <li className="nav-item">
              <Link to={"/admin"} className="nav-link">
                Admin Board
              </Link>
            </li>
          )}

          {showUserBoard && (
            <li className="nav-item">
              <Link to={"/user"} className="nav-link">
                User
              </Link>
            </li>
          )}
        </div>

        {currentUser ? (
          <div className="navbar-nav ml-auto">
            <li className="nav-item">
              <Link to={"/profile"} className="nav-link">
                {currentUser.username}
              </Link>
            </li>
            <li className="nav-item">
              <a href="/login" className="nav-link" onClick={logOut}>
                LogOut
              </a>
            </li>
          </div>
        ) : (
          <div className="navbar-nav ml-auto">
            <li className="nav-item">
              <Link to={"/login"} className="nav-link">
                Login
              </Link>
            </li>

            <li className="nav-item">
              <Link to={"/register"} className="nav-link">
                Sign Up
              </Link>
            </li>
          </div>
        )}
      </nav>

      <div className="container mt-3">
        <Routes>
          <Route path="/" element={<Home/>} />
          <Route path="/home" element={<Home/>} />
          <Route path="/login" element={<Login/>} />
          <Route path="/register" element={<Register/>} />
          <Route path="/profile" element={<Profile/>} />
          <Route path="/user" element={<BoardUser/>} />
           <Route path="/user/view-devices" element={<UserViewDevices />} />
           <Route path="/user/view-software" element={<UserViewSoftware />} />  

          <Route path="/mod" element={<BoardModerator/>} />
          <Route path="/admin" element={<BoardAdmin/>} />
          <Route path="/admin/device-management" element={<DeviceManagementPage />} />
          <Route path="/admin/software-management" element={<SoftwareManagementPage />} />
          <Route path="/admin/lifecycle-management" element={<LifecycleEventManagementPage />} />
          <Route path="/update-device/:deviceId" element={<UpdateDevice/>} />
          <Route path="/update-software/:softwareId" element={<UpdateSoftware/>} />
          <Route path="/update-lifecycleEvent/:eventId" element={<UpdateLifecycleEvent/>} />
          
        </Routes>
      </div>

    </div>
  );
};

export default App;
