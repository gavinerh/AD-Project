import {useHistory} from 'react-router-dom'
 

function Settings(){
    const history = useHistory();
    // set preference

    // update user
    const userDetailsHandler = () => {
        history.push("/updateuser");
    }
 
    return(
        <div>
            <h1>You have reached settings page</h1>
            <button>Set Preference</button>
            <button onClick={userDetailsHandler}>User Settings</button>
        </div>
    )
}

export default Settings;