import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class WeatherViewController {

    @FXML
    private Label cityLabel;

    @FXML
    private Label regionLabel;

    @FXML
    private Label countryLabel;

    @FXML
    private Label latitudeLabel;

    @FXML
    private Label longitudeLabel;

    @FXML
    private Label timezoneLabel;

    @FXML
    private Label localTimeLabel;

    @FXML
    private Label temperatureCLabel;

    @FXML
    private Label temperatureFLabel;

    @FXML
    private Label isDayLabel;

    @FXML
    private Label conditionLabel;

    @FXML
    private Label windMphLabel;

    @FXML
    private Label windKphLabel;

    @FXML
    private Label windDegreeLabel;

    @FXML
    private Label windDirLabel;

    @FXML
    private Label pressureMbLabel;

    @FXML
    private Label pressureInLabel;

    @FXML
    private Label precipMmLabel;

    @FXML
    private Label precipInLabel;

    @FXML
    private Label humidityLabel;

    @FXML
    private Label cloudLabel;

    @FXML
    private Label feelsLikeCLabel;

    @FXML
    private Label feelsLikeFLabel;

    @FXML
    private Label visKmLabel;

    @FXML
    private Label visMilesLabel;

    @FXML
    private Label uvLabel;

    @FXML
    private Label gustMphLabel;

    @FXML
    private Label gustKphLabel;

    @FXML
    private Label airQualityCOLabel;

    @FXML
    private Label airQualityNO2Label;

    @FXML
    private Label airQualityO3Label;

    @FXML
    private ImageView gifImageView;

    private WeatherInfo weatherInfo;
    private GifInfo gifInfo;

    public void setWeatherInfo(WeatherInfo weatherInfo) {
        this.weatherInfo = weatherInfo;
        displayWeatherInfo();
    }

    public void setGifInfo(GifInfo gifInfo) {
        this.gifInfo = gifInfo;
        displayGif();
    }

    private void displayWeatherInfo() {
        Location location = weatherInfo.getLocation();
        Current current = weatherInfo.getCurrent();

        cityLabel.setText(location.getName());
        regionLabel.setText(location.getRegion());
        countryLabel.setText(location.getCountry());
        latitudeLabel.setText(Double.toString(location.getLat()));
        longitudeLabel.setText(Double.toString(location.getLon()));
        timezoneLabel.setText(location.getTz_id());
        localTimeLabel.setText(current.getLast_updated());

        temperatureCLabel.setText(Double.toString(current.getTemp_c()));
        temperatureFLabel.setText(Double.toString(current.getTemp_f()));
        isDayLabel.setText(Integer.toString(current.getIs_day()));
        conditionLabel.setText(current.getCondition() != null ? current.getCondition().getText() : "");
        windMphLabel.setText(Double.toString(current.getWind_mph()));
        windKphLabel.setText(Double.toString(current.getWind_kph()));
        windDegreeLabel.setText(Integer.toString(current.getWind_degree()));
        windDirLabel.setText(current.getWind_dir() != null ? current.getWind_dir() : "");
        pressureMbLabel.setText(Double.toString(current.getPressure_mb()));
        pressureInLabel.setText(Double.toString(current.getPressure_in()));
        precipMmLabel.setText(Double.toString(current.getPrecip_mm()));
        precipInLabel.setText(Double.toString(current.getPrecip_in()));
        humidityLabel.setText(Integer.toString(current.getHumidity()));
        cloudLabel.setText(Integer.toString(current.getCloud()));
        feelsLikeCLabel.setText(Double.toString(current.getFeelslike_c()));
        feelsLikeFLabel.setText(Double.toString(current.getFeelslike_f()));
        visKmLabel.setText(Double.toString(current.getVis_km()));
        visMilesLabel.setText(Double.toString(current.getVis_miles()));
        uvLabel.setText(Integer.toString(current.getUv()));
        gustMphLabel.setText(Double.toString(current.getGust_mph()));
        gustKphLabel.setText(Double.toString(current.getGust_kph()));
        airQualityCOLabel.setText(current.getAir_quality() != null ? Double.toString(current.getAir_quality().getCo()) : "-");
        airQualityNO2Label.setText(current.getAir_quality() != null ? Double.toString(current.getAir_quality().getNo2()) : "-");
        airQualityO3Label.setText(current.getAir_quality() != null ? Double.toString(current.getAir_quality().getO3()) : "-");
    }

    private void displayGif() {
        if (gifInfo != null) {
            String gifUrl = gifInfo.getData().getImages().getOriginal().getUrl();
            System.out.println(gifUrl);
            Image gifImage = new Image(gifUrl);
            gifImageView.setImage(gifImage);
        }
    }

}