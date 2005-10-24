package com.jacob.samples.test;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.*;

/**
 * This example uses the MathTest sample VB COM DLL under
 * the MathProj directory
 * <pre>
 * -Djava.library.path=d:/jacob/release -Dcom.jacob.autogc=false -Dcom.jacob.debug=true
 * </pre>
 */
class math {
	public static void main(String[] args) {
		// deprecated
		// System.runFinalizersOnExit(true);
		Dispatch test = new ActiveXComponent("MathTest.Math");
		testEvents te = new testEvents();
		DispatchEvents de = new DispatchEvents(test, te);
		if (de == null) {
			System.out
					.println("null returned when trying to create DispatchEvents");
		}
		System.out.println(Dispatch.call(test, "Add", new Variant(1),
				new Variant(2)));
		System.out.println(Dispatch.call(test, "Mult", new Variant(2),
				new Variant(2)));
		Variant v = Dispatch.call(test, "Mult", new Variant(2), new Variant(2));
		// this should return false
		System.out.println("v.isNull=" + v.isNull());
		v = Dispatch.call(test, "getNothing");
		// these should return nothing
		System.out.println("v.isNull=" + v.isNull());
		System.out.println("v.toDispatch=" + v.toDispatch());
	}
}

class testEvents {
	public void DoneAdd(Variant[] args) {
		System.out.println("DoneAdd called in java");
	}

	public void DoneMult(Variant[] args) {
		System.out.println("DoneMult called in java");
	}
}
