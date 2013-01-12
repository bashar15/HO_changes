package ho.module.transfer.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerWagesModel {

	private List<PlayerWage> list;

	private PlayerWagesModel() {
	}

	public static PlayerWagesModel create(int playerId, Date from, Date to) {
		PlayerWagesModel model = new PlayerWagesModel();
		model.list = new ArrayList<PlayerWage>();

		List<Date> updates = Calc.getUpdates(Calc.getEconomyDate(), from, to);
		List<Wage> wagesByAge = Wage.getWagesByAge(playerId);

		Map<Integer, Wage> ageWageMap = new HashMap<Integer, Wage>();
		for (Wage wage : wagesByAge) {
			ageWageMap.put(Integer.valueOf(wage.getAge()), wage);
		}

		Date birthDay17 = Calc.get17thBirthday(playerId);

		for (int i = 0; i < updates.size(); i++) {
			Date date = updates.get(i);
			PlayerWage data = new PlayerWage();
			int ageAt = Calc.getAgeAt(birthDay17, date);
			data.setAge(ageAt);
			data.setFinancialUpdateDate(date);
			data.setWeek(HTWeek.getHTWeekByDate(date));
			data.setWage(ageWageMap.get(Integer.valueOf(ageAt)).getWage());
			model.list.add(data);
		}
		return model;
	}

}
