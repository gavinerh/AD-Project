import { Link, Redirect } from 'react-router-dom'
import { Component } from 'react';



function Settings() {

    function updateCategoryHandler() {
        return <Redirect to="/main/settings/updateCategory" />
    }


    return (
        <div>
            <h1>You have reached settings page</h1>
            <button><Link to="/main/settings/updateCategory" onClick={updateCategoryHandler}>Set News Category</Link></button>
            <button >User Settings</button>
        </div>
    )

}

export default Settings;