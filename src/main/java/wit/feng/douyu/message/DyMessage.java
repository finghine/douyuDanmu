package wit.feng.douyu.message;

import java.util.LinkedHashMap;


public class DyMessage {
	  protected String msg;
	    protected String type;
	protected LinkedHashMap<String, String> list;

    public DyMessage() {}

    public DyMessage(String message) {
        this(message, "/", "@=");
    }
    public DyMessage(DyMessage message) {
        this.msg = message.msg;
        this.type = message.type;
        this.list = new LinkedHashMap<>(message.list);
    }

    public DyMessage(String message, String split1, String split2) {
        msg = message;
        list = parser(message, split1, split2);
        type = list.get("type");
    }
    
    public static LinkedHashMap<String, String> parser(String message, String split1, String split2) {
        LinkedHashMap<String, String> list = new LinkedHashMap<>();
        if (message != null) {
            message = message.trim();

            String[] splits1 = message.split(split1);
            for (String s : splits1) {
                String[] splits2 = s.split(split2);
                if (splits2.length == 2) {
                    list.put(splits2[0], splits2[1]);
                }
            }
        }
        return list;
    }

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public LinkedHashMap<String, String> getList() {
		return list;
	}

	public void setList(LinkedHashMap<String, String> list) {
		this.list = list;
	}
}
