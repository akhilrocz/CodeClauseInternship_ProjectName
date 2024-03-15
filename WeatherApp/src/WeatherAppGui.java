import org.json.simple.JSONObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;

import static java.awt.font.TextAttribute.FONT;

public class WeatherAppGui extends JFrame {
    private JSONObject weatherData;
    public WeatherAppGui() {

        //Setting up gui and add title name
        super("WeatherAppGui");

        //Configure the gui to close program process once it has been closed
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //set size of gui in pixels
        setSize(450, 650);

        setLocationRelativeTo(null);

        setLayout(null);

        setResizable(false);

        addGuiComponents();

    }
//Adding gui components
    private void addGuiComponents() {
        //search field

        JTextField JTextFieldForSearch = new JTextField();

        //set the size of our component and location to enter
        JTextFieldForSearch.setBounds(15, 15, 351, 45);
        JTextFieldForSearch.setFont(new Font("Dialog", Font.PLAIN, 24));

        add(JTextFieldForSearch);



        //Display weather image

        JLabel weatherImage = new JLabel(loadImage("src/assets/images/cloudy.png"));
        weatherImage.setBounds(0,125,450,217);
        add(weatherImage);

        //temperature text enter with in JLabel

        JLabel temperatureText = new JLabel("10 C");
        temperatureText.setBounds(0,350,450,54);
        temperatureText.setFont(new Font("Dialog",Font.BOLD,48));
        temperatureText.setHorizontalAlignment(SwingConstants.CENTER);
        add(temperatureText);

        JLabel weatherConditionDesc = new JLabel("Cloudy");
        weatherConditionDesc.setBounds(0,405,450,36);
        weatherConditionDesc.setFont(new Font("Dialog",Font.PLAIN,32));
        weatherConditionDesc.setHorizontalAlignment(SwingConstants.CENTER);
        add(weatherConditionDesc);

        //humidity image

        JLabel humidityImage = new JLabel(loadImage("src/assets/images/humidity.png"));
        humidityImage.setBounds(15,500,74,66);
        add(humidityImage);

        //humidity image text

        JLabel HumidityText = new JLabel("<html><b>Humidity</b> 100%</html>");
        HumidityText.setBounds(90,500,85,55);
        HumidityText.setFont(new Font("Dialog",Font.PLAIN,16));
        add(HumidityText);

        //windspeed image

        JLabel windspeedImage = new JLabel(loadImage("src/assets/images/windspeed.png"));
        windspeedImage.setBounds(220,500,74,66);
        add(windspeedImage);

        //windspeed text

        JLabel windspeedtext = new JLabel("<html><b>WindSpeed</b> 15Km/h</html>");
        windspeedtext.setBounds(310,500,85,55);
        windspeedtext.setFont(new Font("Dialog",Font.PLAIN,16));
        add(windspeedtext);

        //Search Button
        JButton jbutton = new JButton(loadImage("src/assets/images/search.png"));

        jbutton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        jbutton.setBounds(375,13,47,45); // Set bounds: x, y, width, height
        jbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String userInput = JTextFieldForSearch.getText();
                if(userInput.replaceAll("\\s","").length() <=0){
                     return;
                }
                weatherData = WeatherApp.getWeatherData(userInput);

                  //update gui
                //updating weather data
                String weatherCondition = (String) weatherData.get("weather_condition");

                switch(weatherCondition){
                    case "Clear":
                        weatherImage.setIcon(loadImage("src/assets/images/clear.png"));
                        break;
                    case "Cloudy":
                        weatherImage.setIcon(loadImage("src/assets/images/cloudy.png"));
                        break;
                    case "Rain":
                        weatherImage.setIcon(loadImage("src/assets/images/rain.png"));
                        break;
                    case "Snow":
                        weatherImage.setIcon(loadImage("src/assets/images/snow.png"));
                        break;
                }
                //update temperature text
                double temperature = (double) weatherData.get("temperature");
                temperatureText.setText(temperature + "C");

                //Update weather condition text
                weatherConditionDesc.setText(weatherCondition);

                //update humidity text

                long humidity = (long) weatherData.get("humidity");
                HumidityText.setText("<html><b>Humidity</b> " + humidity + "%</html>");

                //update windspeed text
                double windspeed = (double) weatherData.get("windspeed");
                windspeedtext.setText("<html><b>WindSpeed</b> " + windspeed + "Km/h</html>");
            }
        });
            add(jbutton);
    }

    private ImageIcon loadImage(String resourcePath){
//used to create images in gui components
        try {
            BufferedImage image = ImageIO.read(new File(resourcePath));
            return new ImageIcon(image);
        }catch(IOException e){
           e.printStackTrace();
        }
        System.out.println("Cannot load image");
        return null;
    }

}

