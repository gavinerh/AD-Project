import UserDataService from "../service/UserDataService";
import { useEffect, useState } from 'react'
import { useHistory } from 'react-router-dom';
import AuthenticationService from "../service/AuthenticationService";
import Modal from "./Modal";

function UserDetails() {
    const history = useHistory();
    const [userCredential, setUserCredential] = useState({
        email: '',
        password: '',
        phone: '',
        name: '',
        error: null
    });
    const [isloading, setIsloading] = useState('');
    useEffect(() => {
        UserDataService.getUser(UserDataService)
            .then(response => {
                const userDetails = response.data;
                setUserCredential({
                    ...userCredential,
                    email: userDetails.email,
                    password: userDetails.password,
                    phone: userDetails.phone,
                    name: userDetails.name
                });
            })
            .catch(error => {
                console.log("token has expired");
                if (!AuthenticationService.checkJwtValidity()) {
                    setIsloading(<div><Modal /></div>)
                } else {
                    setIsloading(<p>Loading ...</p>)
                }
            })
    }, [])

    const nameChangeHandler = (event) => {
        setUserCredential({
            ...userCredential,
            name: event.target.value,
        })
    }
    const phoneChangeHandler = (event) => {
        setUserCredential({
            ...userCredential,
            phone: event.target.value,
        })
    }
    const passwordChangeHandler = (event) => {
        setUserCredential({
            ...userCredential,
            password: event.target.value,
        })
    }

    const submitHandler = (event) => {
        // get user details and send to server
        event.preventDefault()
        if (userCredential.password === null || userCredential.password === "") {
            setUserCredential({
                ...userCredential,
                error: "Please fill in password"
            })
        } else {
            UserDataService.updateUser(userCredential.name, userCredential.phone, userCredential.email, userCredential.password)
                .then(response => {
                    console.log(response);
                    history.push('/main');
                }).catch(error => {
                    console.log("token has expired");
                    if (!AuthenticationService.checkJwtValidity()) {
                        setIsloading(<div><Modal /></div>)
                    }
                })
        }
    }

    return (
        <div className="container p-3">
            <main>
                <div className="row g-5">
                    {!isloading ?
                        <div className="col-md-6">
                            <h4 className="mb-3">My account</h4>
                            <form onSubmit={submitHandler}>
                                <div className="row g-3">
                                    <div className="col-12">
                                        <label htmlFor="email" className="form-label">Email</label>
                                        <input type="email" readOnly className="form-control" id="email" value={userCredential.email || ""} />
                                    </div>

                                    <div className="col-12">
                                        <label htmlFor="password" className="form-label">Password</label>
                                        <input type="password" className="form-control" id="password" onChange={passwordChangeHandler} required />
                                        {userCredential.error && <span className="fw-bold text-danger">{userCredential.error}</span>}
                                    </div>

                                    <div className="col-12">
                                        <label htmlFor="name" className="form-label">Name <span className="text-muted">(Optional)</span></label>
                                        <input type="text" className="form-control" id="name" value={userCredential.name || ""} onChange={nameChangeHandler} />
                                    </div>

                                    <div className="col-12">
                                        <label htmlFor="phone" className="form-label">Phone <span className="text-muted">(Optional)</span></label>
                                        <input type="text" className="form-control" id="phone" value={userCredential.phone || ""} onChange={phoneChangeHandler} />
                                    </div>

                                    <button className="btn btn-primary" type="submit">Update</button>
                                </div>
                            </form>
                        </div>
                        : isloading}
                </div>
            </main>
        </div>
    )
}

export default UserDetails;