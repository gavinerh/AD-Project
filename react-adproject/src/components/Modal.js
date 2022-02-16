import React from 'react';
import { Component } from 'react';
import { Link, Redirect } from 'react-router-dom';
import AuthenticationService from '../service/AuthenticationService';


class Modal extends Component {
    constructor(props) {
        super(props);
        this.logoutProcess = this.logoutProcess.bind(this);
    }
    // if(!open) return null;

    logoutProcess() {
        AuthenticationService.removeUserSession();
        AuthenticationService.removeJwtToken();
        return (<Redirect to="/login" />)
    }
    render() {
        return (
            <div className="modal modal-signin position-static d-block py-5">
                <div className="modal-dialog">
                    <div className="modal-content rounded-5 shadow">
                        <div className="modal-header pt-5 px-5 border-bottom-0">
                            <h1 className="fw-normal">Session has expired!!</h1>
                        </div>

                        <div className="modal-body p-5 pt-0">
                            <button className="w-100 mb-2 btn btn-lg rounded-4 btn-primary" type="submit"><Link to="/login/" style={{ color: 'white' }} onClick={this.logoutProcess}>Login Again</Link></button>

                        </div>
                    </div>
                </div>
            </div>
        )
    }
}

export default Modal;