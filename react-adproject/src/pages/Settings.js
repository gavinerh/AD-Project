import { Link, Redirect } from 'react-router-dom'
import { Component } from 'react';



function Settings() {

    function updateCategoryHandler() {
        return <Redirect to="/main/settings/updateCategory" />
    }


    return (
        <div>
            <h1>You have reached settings page</h1>
            <button >Set News Category:</button>
            <button><Link to="/main/settings/updateCategory" onClick={updateCategoryHandler}>User Settings</Link></button>
        </div>
    )

}

export default Settings;