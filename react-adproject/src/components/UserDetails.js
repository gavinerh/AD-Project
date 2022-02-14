import UserDataService from "../service/UserDataService";
import { useEffect, useState } from 'react'
import { useHistory } from 'react-router-dom';

function UserDetails() {
    const history = useHistory();
    const [userCredential, setUserCredential] = useState({
        email: '',
        password: '',
        phone: '',
        name: ''
    });
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
            .catch()
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
        UserDataService.updateUser(userCredential.name, userCredential.phone, userCredential.email, userCredential.password)
            .then(response => {
                console.log(response);
                history.push('/main');
            }).catch(response => {
                // stay at this page
                console.log(response);
            })
    }


    //validation of password
    return (

        <div className="container p-3">
            <main>
                <div className="row g-5">
                    <div className="col-md-6">
                        <h4 className="mb-3">Update user details</h4>
                        <form>
                            <div className="row g-3">
                                <div className="col-12">
                                    <label htmlFor="email" className="form-label">Email</label>
                                    <input type="email" readOnly className="form-control" id="email" value={userCredential.email || ""} />
                                </div>

                                <div className="col-12">
                                    <label htmlFor="password" className="form-label">Password</label>
                                    <input type="password" className="form-control" id="password" value={userCredential.password || ""} onChange={passwordChangeHandler} />
                                </div>

                                <div className="col-12">
                                    <label htmlFor="name" className="form-label">Name <span className="text-muted">(Optional)</span></label>
                                    <input type="text" className="form-control" id="name" value={userCredential.name || ""} onChange={nameChangeHandler} />
                                </div>

                                <div className="col-12">
                                    <label htmlFor="phone" className="form-label">Phone <span className="text-muted">(Optional)</span></label>
                                    <input type="text" className="form-control" id="phone" value={userCredential.phone || ""} onChange={phoneChangeHandler} />
                                </div>

                                <button className="btn btn-primary" type="submit" onClick={submitHandler}>Update</button>
                            </div>
                        </form>
                    </div>
                </div>
            </main>
        </div>
    )
}

export default UserDetails;