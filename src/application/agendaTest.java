package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import jfxtras.icalendarfx.VCalendar;
import jfxtras.scene.control.agenda.icalendar.ICalendarAgenda;


public class agendaTest implements Initializable {
	@FXML VCalendar calendar = new VCalendar();
	@FXML ICalendarAgenda agenda = new ICalendarAgenda(calendar);
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	}

}
