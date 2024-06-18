import React, { useState, useEffect } from "react";
import { Routes, Route, Link } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import "./App.css";

import EventBus from "./common/EventBus";
import AuthService from "./services/auth.service";
import BoardManagement from "./components/BoardManagement";
import BoardTechnicalsupport from "./components/BoardTechnicalsupport";
import BoardAdmin from "./components/BoardAdmin";
import BoardUser from "./components/BoardUser";
import Profile from "./components/Profile";
import Register from "./components/Register";
import Login from "./components/Login";
import Home from "./components/Home";
import DeviceManagementPage from "./components/DeviceManagementPage";
import SoftwareManagementPage from "./components/SoftwareManagementPage";
import LifecycleEventManagementPage from "./components/LifecycleEventManagementPage";
import UpdateDevice from "./components/UpdateDevice";
import UpdateSoftware from "./components/UpdateSoftware";
import UpdateLifecycleEvent from "./components/UpdateLifecycleEvent";
import UserViewDevices from "./components/UserViewDevices";
import UserViewSoftware from "./components/UserViewSoftware";
import LogFaultForm from "./components/LogFault";
import UpdateLogFault from "./components/UpdateLogFault";
import ViewEndOfSupportDates from "./components/ViewEndOfSupportDates";
import UpdateUserRole from "./components/UpdateUserRole";

const App = () => {
  const [showManagementBoard, setShowManagementBoard] = useState(false);
  const [showAdminBoard, setShowAdminBoard] = useState(false);
  const [showTechnicalsupportBoard, setTechnicalsupportBoard] = useState(false);
  const [showUserBoard, setShowUserBoard] = useState(false);
  const [currentUser, setCurrentUser] = useState(undefined);

  useEffect(() => {
    const user = AuthService.getCurrentUser();

    if (user) {
      setShowUserBoard(user.roles.includes("ROLE_USER"));
      setCurrentUser(user);
      setShowManagementBoard(user.roles.includes("ROLE_MANAGEMENT"));
      setShowAdminBoard(user.roles.includes("ROLE_ADMIN"));
      setTechnicalsupportBoard(user.roles.includes("ROLE_TECHNICALSUPPORT"));
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
    setTechnicalsupportBoard(false);
    setCurrentUser(undefined);
  };

  return (
    <div>
      <nav className="navbar navbar-expand-lg navbar-dark navbar-custom">
        <Link to={"/"} className="navbar-brand">
          LLT Project
        </Link>
        <button className="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
          <span className="navbar-toggler-icon"></span>
        </button>
        <div className="collapse navbar-collapse" id="navbarNav">
          <ul className="navbar-nav mr-auto">
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

            {showTechnicalsupportBoard && (
              <li className="nav-item">
                <Link to={"/tech"} className="nav-link">
                  TechnicalSupport Board
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
          </ul>
          
          <div className="navbar-nav ml-auto">
            {currentUser ? (
              <>
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
              </>
            ) : (
              <>
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
              </>
            )}
          </div>
        </div>
      </nav>

      <div className="containerApp">
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/home" element={<Home />} />
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Register />} />
          <Route path="/profile" element={<Profile />} />
          <Route path="/user" element={<BoardUser />} />
          <Route path="/user/view-devices" element={<UserViewDevices />} />
          <Route path="/user/view-software" element={<UserViewSoftware />} />
          <Route path="/mod" element={<BoardManagement />} />
          <Route path="/tech" element={<BoardTechnicalsupport />} />
          <Route path="/admin" element={<BoardAdmin />} />
          <Route path="/admin/device-management" element={<DeviceManagementPage />} />
          <Route path="/admin/software-management" element={<SoftwareManagementPage />} />
          <Route path="/admin/lifecycle-management" element={<LifecycleEventManagementPage />} />
          <Route path="/update-device/:deviceId" element={<UpdateDevice />} />
          <Route path="/update-software/:softwareId" element={<UpdateSoftware />} />
          <Route path="/update-lifecycleEvent/:eventId" element={<UpdateLifecycleEvent />} />
          <Route path="/technicalsupport/log-fault" element={<LogFaultForm />} />
          <Route path="/technicalsupport/update-log-fault" element={<UpdateLogFault />} />
          <Route path="/technicalsupport/view-end-of-support-dates" element={<ViewEndOfSupportDates />} />
          <Route path="/admin/Update-Role" element={<UpdateUserRole />} />
          
        </Routes>
      </div>
    </div>
  );
};

export default App;
