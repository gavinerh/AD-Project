import React, { Component } from 'react'

class WeatherComponent extends Component {
    constructor(props) {
        super(props);

        this.state = {
            content: [],
            error: null,
            isLoaded: false
        };

    }

    componentDidMount() {
        fetch("https://api.data.gov.sg/v1/environment/24-hour-weather-forecast")
            .then((res) => res.json())
            .then(json =>
                json.items.map(info => ({
                    timestamp: `${info.timestamp}`,
                    forecast: `${info.general.forecast}`,
                    low: `${info.general.temperature.low}`,
                    high: `${info.general.temperature.high}`
                }))
            )
            .then((info) => {
                this.setState({
                    content: info,
                    isLoaded: true
                });
            })
            .catch(error => this.setState({ error: error.message, isLoaded: true }));
    }



    render() {
        const { isLoaded, content, error } = this.state;

        return (
            <div>
                {/* weather icon */}
                <svg xmlns="http://www.w3.org/2000/svg" style={{ display: 'none' }}>
                    <symbol id="thermometer" viewBox="0 0 16 16">
                        <path d="M9.5 12.5a1.5 1.5 0 1 1-2-1.415V6.5a.5.5 0 0 1 1 0v4.585a1.5 1.5 0 0 1 1 1.415z" />
                        <path d="M5.5 2.5a2.5 2.5 0 0 1 5 0v7.55a3.5 3.5 0 1 1-5 0V2.5zM8 1a1.5 1.5 0 0 0-1.5 1.5v7.987l-.167.15a2.5 2.5 0 1 0 3.333 0l-.166-.15V2.5A1.5 1.5 0 0 0 8 1z" />
                    </symbol>
                </svg>

                {/* display weather */}
                <div className="nav-item">
                    {error && (<div>{`There is a problem loading the weather data: ${error}`}</div>)}
                    {isLoaded ? (
                        content.map(content => {
                            const { timestamp, forecast, low, high } = content;
                            return (
                                <div className='text-start' key={timestamp}>
                                    <strong>{forecast}</strong><br />
                                    <svg className="bi bi-thermometer-half" width="18" height="18">
                                        <use xlinkHref="#thermometer" />
                                    </svg>
                                    {low} - {high} &#8451;
                                </div>
                            );
                        })
                    ) : (
                        <span className="nav-item">Loading weather....</span>
                    )}
                </div>
            </div>
        );
    }
}

export default WeatherComponent