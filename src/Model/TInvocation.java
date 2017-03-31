package Model;

public class TInvocation {
	private int caller_id;
	private String func_owner;
	private String func_name;
	public TInvocation(int call_id, String type, String name) {
		// TODO Auto-generated constructor stub
		caller_id=call_id;
		func_owner=type;
		func_name=name;
	}
	public int getCaller_id() {
		return caller_id;
	}
	public String getFunc_owner() {
		return func_owner;
	}
	public String getFunc_name() {
		return func_name;
	}
}
