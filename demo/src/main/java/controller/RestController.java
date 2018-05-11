package controller;

import java.util.HashMap;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import static org.springframework.util.Assert.notNull;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/counter")
public class RestController {
	private HashMap<String, Integer> counters = new HashMap<>();
	
	@RequestMapping(value = "/increment/{name}", method = RequestMethod.PATCH)
	public ResponseEntity<String> increment(@PathVariable final String name){
		notNull(name);
		Integer integer = counters.get(name);
		if(integer != null){
			integer += 1;
			counters.replace(name,integer);
			return ResponseEntity.ok("Done");
		}
		return ResponseEntity.badRequest().body("Not found:" + name);
	}

	@RequestMapping(value = "/list/{name}", method = RequestMethod.GET)
	public ResponseEntity<String> getValue(@PathVariable final String name){
		notNull(name);
		Integer integer = counters.get(name);
		if(integer != null){
			return ResponseEntity.ok(name + ":" + integer);
		}
		return ResponseEntity.badRequest().body("Not found:" + name);
	}

	@RequestMapping(value = "/list-all", method = RequestMethod.GET)
	public ResponseEntity<String> listAll(){
		StringBuilder sb = new StringBuilder();
		counters.entrySet().forEach(a -> sb.append(a.getKey()).append(":").append(a.getValue()).append(","));
		String value = sb.toString();

		return ResponseEntity.ok(value.substring(0,value.length()-2));
	}

	@RequestMapping(value = "/add/{name}/{count}", method = RequestMethod.POST)
	public ResponseEntity<String> add(@PathVariable(value = "name") final String name, @PathVariable(value = "count") final String count){
		notNull(name);
		notNull(count);
		try{
			counters.put(name,Integer.parseInt(count));
			return ResponseEntity.ok("Done");
		}catch (NumberFormatException e){
			return ResponseEntity.badRequest().body("count should be a number");
		}

	}


}
