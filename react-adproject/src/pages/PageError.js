import React from 'react';
import errorImage from '../assets/errorimage-sm.png';

const PageError = () => {
    return (
        <div className="flex-shrink-0">
            <div className="container">
                <h1 className="mt-5">ERROR</h1>
                <p className="lead">Sorry, we couldn't find the page you were looking for :(<br/>
                Go back to the <a href="javascript:history.back()">previous page.</a></p>
                <img className='img-fluid mb-3' src={errorImage} alt="Error" />
                <p>
                <small className='text-muted'>Illustration by <a href="https://icons8.com/illustrations/author/5eb423ee01d03600140dff88">Tatiana Vinogradova</a> from <a href="https://icons8.com/illustrations">Ouch!</a></small>
                </p>
            </div>
        </div>
    )
}

export default PageError;