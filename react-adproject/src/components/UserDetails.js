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


    return (
        <form>
            <table>
                <tr>
                    <td>Email: </td>
                    <td><input disabled type="email" value={userCredential.email} /></td>
                </tr>
                <tr>
                    <td>Name: </td>
                    <td><input type="text" value={userCredential.name} onChange={nameChangeHandler} /></td>
                </tr>
                <tr>
                    <td>Password: </td>
                    <td><input type="password" value={userCredential.password} onChange={passwordChangeHandler} /></td>
                </tr>
                <tr>
                    <td>Phone: </td>
                    <td><input type="number" value={userCredential.phone} onChange={phoneChangeHandler} /></td>
                </tr>
                <tr>
                    <td><button onClick={submitHandler}>Submit</button></td>
                </tr>
            </table>
            {/* Email:<input disabled type="email" value={userCredential.email} />
            Name: <input type="text" value={userCredential.name} onChange={nameChangeHandler} />
            Password: <input type="password" value={userCredential.password} onChange={passwordChangeHandler} />
            Phone:<input type="number" value={userCredential.phone} onChange={phoneChangeHandler} /><button onClick={submitHandler}>Submit</button> */}
        </form>
    )
}

export default UserDetails;