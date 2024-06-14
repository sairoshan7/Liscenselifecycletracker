import React, { useState } from "react";
import UpdateRoleService from "../services/UpdateRoleService";
 
const UpdateUserRole = () => {
  const [userId, setUserId] = useState("");
  const [roleId, setRoleId] = useState("");
  const [roleName, setRoleName] = useState("");
  const [message, setMessage] = useState("");
  const [error, setError] = useState("");
 
  const handleSubmit = async (e) => {
    e.preventDefault();
    setMessage("");
    setError("");
 
    try {
      await UpdateRoleService.updateUserRole(userId, { id: roleId, name: roleName });
      setMessage("Role updated successfully!");
    } catch (error) {
      if (error.response && error.response.status === 404) {
        setError("User not found.");
      } else {
        setError("An error occurred while updating the role.");
      }
    }
  };
 
  return (
    <div className="container mt-5">
      <div className="row justify-content-center">
        <div className="col-lg-6">
          <div className="card p-4 rounded">
            <h2 className="mb-4">Update User Role</h2>
            <form onSubmit={handleSubmit}>
              <div className="form-group">
                <label>User ID:</label>
                <input
                  type="number"
                  className="form-control"
                  value={userId}
                  onChange={(e) => setUserId(e.target.value)}
                  required
                />
              </div>
              <div className="form-group">
                <label>Role ID:</label>
                <input
                  type="number"
                  className="form-control"
                  value={roleId}
                  onChange={(e) => setRoleId(e.target.value)}
                  required
                />
              </div>
              <div className="form-group">
                <label>Role Name:</label>
                <input
                  type="text"
                  className="form-control"
                  value={roleName} // Ensure that roleName is set properly
                  onChange={(e) => setRoleName(e.target.value)} // Corrected the onChange handler
                  required
                />
              </div>
              <button type="submit" className="btn btn-primary">Update Role</button>
            </form>
            {message && <div className="mt-3 alert alert-info">{message}</div>}
            {error && <div className="mt-3 alert alert-danger">{error}</div>}
          </div>
        </div>
      </div>
    </div>
  );
};
 
export default UpdateUserRole;
