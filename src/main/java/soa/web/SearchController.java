package soa.web;

import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class SearchController {

	@Autowired
	  private ProducerTemplate producerTemplate;

	@RequestMapping("/")
    public String index() {
        return "index";
    }


    @RequestMapping(value="/search")
    @ResponseBody
    public Object search(@RequestParam("q") String q) {

	int i = q.indexOf(":");
	int max = Integer.parseInt(q.substring(i+1));
	q = q.substring(0, i-3);
	Map<String,Object> headers = new HashMap<String,Object>();
	headers.put("CamelTwitterCount",max);
        headers.put("CamelTwitterKeywords",q);
        
        return producerTemplate.requestBodyAndHeader("direct:search", "", headers);
    }
}
