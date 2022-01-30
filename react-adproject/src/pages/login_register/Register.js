import React, {Component} from 'react'
import {Formik, Form, Field, ErrorMessage} from 'formik'
import UserDataService from '../../service/UserDataService';

class RegisterComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            name: '',
            email: '',
            password: '',
            passwordConfirmation: '',
            errorMessage: ''
        }

        this.onSubmit = this.onSubmit.bind(this);
        this.validate = this.validate.bind(this);
        this.isEmailRegistered = false
    }

    validate(values) { //formik function
        let errors = {}
        if(!values.name) {
            errors.name = 'Enter your name'
        }

        if(!values.email) {
            errors.email = 'Enter your email address'
        } else if (!/^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,4}$/i.test(values.email)) {
            errors.email = 'Enter a valid email address';
        }

        if(!values.password) {
            errors.password = 'Enter password'
        } else if(values.password.length < 2) {
            errors.password = 'Password should be at least 2 characters'
        }

        if(!values.passwordConfirmation) {
            errors.passwordConfirmation = 'Enter password confirmation'
        } else if(values.passwordConfirmation !== values.password) {
            errors.passwordConfirmation = 'Passwords do not match'
        }

        return errors
    }

    onSubmit(values) {
        let user = {
            name: values.name,
            email: values.email,
            password: values.password
        }

        UserDataService.createUser(user)
        .then(() => {
            this.isEmailRegistered = false
            this.props.history.push('/login') //redirect to login page
        }) 
        .catch(error => this.handleError(error));

    }

    handleError(error) {
        if(error.response.status === 409) {
            this.isEmailRegistered = true
            this.setState({
                errorMessage : 'Email is already registered'
            })
        }
    }

    render() {
        let {name, email, password, passwordConfirmation} = this.state
        return (
            <div className="modal modal-signin position-static d-block py-5">
                <div className="modal-dialog">
                    <div className="modal-content rounded-5 shadow">
                        <div className="modal-header pt-5 px-5 border-bottom-0">
                            <h1 className="my-brand fw-normal">NEWSBOOK</h1>
                        </div>
                        <div className="modal-header px-5 border-bottom-0">
                            <h2 className="fs-4">Sign Up</h2>
                        </div>
                        
                        <div className="modal-body p-5 pt-0">
                            <Formik
                                initialValues={{name, email, password, passwordConfirmation}}
                                onSubmit={this.onSubmit}
                                validateOnChange={false}
                                validateOnBlur={false}
                                validate={this.validate}
                                enableReinitialize={true}
                            >
                                {
                                    () => (
                                        <Form>
                                            <div className="form-floating mb-3">
                                                <Field name="name" type="string" className="form-control rounded-4" placeholder="Name" />
                                                <label htmlFor="floatingInput">Name</label>
                                                <ErrorMessage name="name">{msg => <div className="fw-bold text-danger">{msg}</div>}</ErrorMessage>
                                            </div>

                                            <div className="form-floating mb-3">
                                                <Field name="email" type="email" className="form-control rounded-4" placeholder="Email address"  />
                                                <label htmlFor="floatingInput">Email address</label>
                                                <ErrorMessage name="email">{msg => <div className="fw-bold text-danger">{msg}</div>}</ErrorMessage>
                                                {this.isEmailRegistered && <span className="fw-bold text-danger">{this.state.errorMessage}</span>}
                                            </div>

                                            <div className="form-floating mb-3">
                                                <Field name="password" type="password" className="form-control rounded-4" placeholder="Password" />
                                                <label htmlFor="floatingPassword">Password</label>
                                                <ErrorMessage name="password">{msg => <div className="fw-bold text-danger">{msg}</div>}</ErrorMessage>
                                            </div>

                                            <div className="form-floating mb-3">
                                                <Field name="passwordConfirmation" type="password" className="form-control rounded-4" placeholder="Confirm password" />
                                                <label htmlFor="floatingInput">Confirm password</label>
                                                <ErrorMessage name="passwordConfirmation">{msg => <div className="fw-bold text-danger">{msg}</div>}</ErrorMessage>
                                            </div>

                                            <button className="w-100 mb-2 btn btn-lg rounded-4 btn-primary" type="submit">Sign up</button>
                                        </Form>
                                    )
                                }
                            </Formik>

                        </div>
                    </div>
                </div>
            </div>
        )
    }
}

export default RegisterComponent