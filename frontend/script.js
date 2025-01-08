document.getElementById("fetchWeather").addEventListener("click", async () => {
    const weatherDataDiv = document.getElementById("weatherData");
    weatherDataDiv.textContent = "Fetching weather data...";

    try {
        const response = await fetch("http://localhost:8080/weather");
        if (!response.ok) {
            throw new Error(`Server error: ${response.status}`);
        }

        const data = await response.text();
        weatherDataDiv.textContent = data;
    } catch (error) {
        weatherDataDiv.textContent = `Error: ${error.message}`;
    }
});
