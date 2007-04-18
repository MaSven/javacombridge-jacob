/*
 * Copyright (c) 1999-2004 Sourceforge JACOB Project.
 * All rights reserved. Originator: Dan Adler (http://danadler.com).
 * Get more information about JACOB at http://sourceforge.net/projects/jacob-project
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */
package com.jacob.com;

import java.util.Date;

/**
 * The multi-format data type used for all call backs and most communications
 * between Java and COM. It provides a single class that can handle all data
 * types.
 * <p>
 * PROPVARIANT introduces new types so eventually Variant will need to be 
 * upgraded to support PropVariant types.
 * http://blogs.msdn.com/benkaras/archive/2006/09/13/749962.aspx
 * <p>
 * This object no longer implements Serializable because serialization is broken 
 * (and has been since 2000/xp).  The underlying
 * marshalling/unmarshalling code is broken in the JNI layer.
 */
public class Variant extends JacobObject {

	/**
     * Use this constant for optional parameters
     */
    public final static com.jacob.com.Variant DEFAULT;

    /**
     * Same than {@link #DEFAULT}
     */
    public final static com.jacob.com.Variant VT_MISSING;
    
    /**
     * Use for true/false variant parameters
     */
    public final static com.jacob.com.Variant VT_TRUE = new com.jacob.com.Variant(
            true);

    /**
     * Use for true/false variant parameters
     */
    public final static com.jacob.com.Variant VT_FALSE = new com.jacob.com.Variant(
            false);

    /*
     * do the run time definition of DEFAULT and MISSING
     */
    static {
        com.jacob.com.Variant vtMissing = new com.jacob.com.Variant();
        vtMissing.putVariantNoParam();
        DEFAULT = vtMissing;
        VT_MISSING = vtMissing;
    }
    
    /**
     * Pointer to MS struct.
     */
    int m_pVariant = 0;

    /** variant's type is empty : equivalent to VB Nothing and VT_EMPTY*/
    public static final short VariantEmpty = 0;

    /** variant's type is null : equivalent to VB Null and VT_NULL */
    public static final short VariantNull = 1;

    /** variant's type is short VT_I2*/
    public static final short VariantShort = 2;

    /** variant's type is int VT_I4, a Long in VC*/
    public static final short VariantInt = 3;

    /** variant's type is float VT_R4*/
    public static final short VariantFloat = 4;

    /** variant's type is double VT_R8*/
    public static final short VariantDouble = 5;

    /** variant's type is currency VT_CY */
    public static final short VariantCurrency = 6;

    /** variant's type is date VT_DATE */
    public static final short VariantDate = 7;

    /** variant's type is string  also known as VT_BSTR */
    public static final short VariantString = 8;

    /** variant's type is dispatch VT_DISPATCH*/
    public static final short VariantDispatch = 9;

    /** variant's type is error VT_ERROR */
    public static final short VariantError = 10;

    /** variant's type is boolean VT_BOOL */
    public static final short VariantBoolean = 11;

    /** variant's type is variant it encapsulate another variant VT_VARIANT*/
    public static final short VariantVariant = 12;

    /** variant's type is object VT_UNKNOWN*/
    public static final short VariantObject = 13;

    /** variant's type is byte VT_UI1 */
    public static final short VariantByte = 17;

    /** what is this? */
    public static final short VariantTypeMask = 4095;

    /** variant's type is array */
    public static final short VariantArray = 8192;

    /** variant's type is a reference (to IDispatch?) */
    public static final short VariantByref = 16384;

    /** 
     * @deprecated should use changeType() followed by getInt()
     * @return the value of this variant as an int 
     * (after possible conversion)
     */
    public int toInt(){
    	changeType(VariantInt);
    	return getInt();
    }

    /** 
     * @deprecated should use changeType() followed by getDate()
     * @return the value of this variant as a date 
     * (after possible conversion)
     */
    public double toDate(){
    	changeType(VariantDate);
    	return getDate();
    }

    /**
     * Returns the windows time contained in this Variant as a Java Date
     * converts to a date like many of the other toXXX() methods
     * SF 959382.
     * <p>
     * This method added 12/2005 for possible use by jacobgen instead of its conversion code
     * <p>
     * This does not convert the data
     * @deprecated  callers should use getDate()
     * @return java.util.Date version of this variant if it is a date, otherwise null
     * 
     */
    public Date toJavaDate(){
    	changeType(Variant.VariantDate);
    	return getJavaDate();
    }
    
    
    /** 
     * @deprecated should be replaced by changeType() followed by getBoolean()
     * @return the value of this variant as boolean (after possible conversion) 
     */
    public boolean toBoolean(){
    	changeType(Variant.VariantBoolean);
    	return getBoolean();
    }

    /** @return the value of this variant as an enumeration (java style) */
    public native EnumVariant toEnumVariant();

    /**
     * This method would have returned null if the type was VT_NULL.
     * But because we return null if the data is not of the right type,
     *  this method should have always returned null
     *  @deprecated method never did anything
     */
    public void getNull() {};

    /** 
     * Set this Variant's type to VT_NULL (the VB equivalent of NULL) 
     * */
    private native void putVariantNull();

    /** 
     * Set this Variant's type to VT_NULL (the VB equivalent of NULL) 
     * */
    public void putNull(){
    	// verify we aren't released yet
    	getvt();
    	putVariantNull();
    }
    
    /**
     * @deprecated No longer used
     * @return null !
     */
    public native Variant cloneIndirect();

    /** 
     * @deprecated should call changeType() then getDouble()
     * @return the content of this variant as a double  
     * (after possible conversion)
     **/
    public double toDouble(){
    	changeType(Variant.VariantDouble);
    	return getDouble();
    }

    /**
     * @deprecated should be replaced by changeType() followed by getCurrency
     * @return the content of this variant as a long reprensenting a monetary
     *         amount
     */
    public long toCurrency(){
    	changeType(Variant.VariantCurrency);
    	return getCurrency();
    }

    /**
     * @deprecated superceded by SafeArray
     * @param in doesn't matter because this method does nothing
     * @throws com.jacob.com.NotImplementedException
     */
    public void putVariantArray(Variant[] in) {
        throw new NotImplementedException("Not implemented");
    }

    /**
     * @deprecated superceded by SafeArray
     * @return never returns anything
     * @throws com.jacob.com.NotImplementedException
     */
    public Variant[] getVariantArray() {
        throw new NotImplementedException("Not implemented");
    }

    /**
     * Exists to support jacobgen.
     * This would be deprecated if it weren't for jacobgen
     * @deprecated superceded by "this"
     * @return this same object
     */
    public Variant toVariant() { return this; }
    
    /**
     * @deprecated superceded by SafeArray
     * @param in doesn't matter because this method does nothing
     * @throws com.jacob.com.NotImplementedException
     */
    public void putByteArray(Object in) {
        throw new NotImplementedException("Not implemented");
    }

    /**
     * set the content of this variant to a short (VT_I2|VT_BYREF)
     * @param in
     */
    private native void putVariantShortRef(short in);
    
    /**
     * set the content of this variant to a short (VT_I2|VT_BYREF)
     * @param in
     */
    public void putShortRef(short in){
    	// verify we aren't released
    	getvt();
    	putVariantShortRef(in);
    }

    /**
     * set the content of this variant to an int (VT_I4|VT_BYREF)
     * @param in
     */
    private native void putVariantIntRef(int in);
    
    /**
     * set the content of this variant to an int (VT_I4|VT_BYREF)
     * @param in
     */
    public void putIntRef(int in){
    	// verify we aren't released
    	getvt();
    	putVariantIntRef(in);
    }

    /**
     * set the content of this variant to a double (VT_R8|VT_BYREF)
     * @param in
     */
    private native void putVariantDoubleRef(double in);
    
    /**
     * set the content of this variant to a double (VT_R8|VT_BYREF)
     * @param in
     */
    public void putDoubleRef(double in){
    	// verify we aren't released
    	getvt();
    	putVariantDoubleRef(in);
    }

    /**
     * set the content of this variant to a date (VT_DATE|VT_BYREF)
     * @param in
     */
    private native void putVariantDateRef(double in);

    /**
     * set the content of this variant to a date (VT_DATE|VT_BYREF)
     * @param in
     */
    public void putDateRef(double in){
    	// verify we aren't released
    	getvt();
    	putVariantDateRef(in);
    }

    /**
     * converts a java date to a windows time and calls putDateRef(double)
     * SF 959382 
     * @throws IllegalArgumentException if inDate = null
     * @param inDate a Java date to be converted
     */
    public void putDateRef(Date inDate){
    	if (inDate == null){
    		throw new IllegalArgumentException("Cannot put null in as windows date");
    		// do nothing
    	} else {
    		putDateRef(DateUtilities.convertDateToWindowsTime(inDate));
    	}
    }
    
    /**
     * set the content of this variant to a string (VT_BSTR|VT_BYREF)
     * @param in
     */
    private native void putVariantStringRef(String in);

    /**
     * set the content of this variant to a string (VT_BSTR|VT_BYREF)
     * @param in
     */
    public void putStringRef(String in){
    	// verify we aren't released
    	getvt();
    	putVariantStringRef(in);
    }
    
	/**
	 * Puts a variant into this variant making it type VT_VARIANT.
	 * Added 1.12 pre 6
	 * 
	 * @throws IllegalArgumentException
	 *             if inVariant = null
	 * @param inVariant A variant that is to be referenced by this variant
	 */
	public void putVariant(Variant inVariant) {
		if (inVariant == null) {
			throw new IllegalArgumentException("Cannot put null in as a variant");
		} else {
			putVariantVariant(inVariant);
		}
	}

	/**
	 * All VariantVariant type variants are BYREF.
	 * 
	 * Set the content of this variant to a string (VT_VARIANT|VT_BYREF).
	 * 
	 * Added 1.12 pre 6 - VT_VARIANT support is at an alpha level
	 * @param in variant to be wrapped 
	 * 
	 */
	private native void putVariantVariant(Variant in);

	/**
	 * Used to get the value from a windows type of VT_VARIANT
	 * or a jacob Variant type of VariantVariant.
	 * Added 1.12 pre 6 - VT_VARIANT support is at an alpha level
	 * @return Object a java Object that represents the content of the enclosed Variant
	 */
	public Object getVariant() {
		if ((this.getvt() & VariantVariant) == VariantVariant
				&& (this.getvt() & VariantByref) == VariantByref) {
			if (JacobObject.isDebugEnabled()){
				JacobObject.debug("About to call getVariantVariant()");
			}
			Variant enclosedVariant = getVariantVariant();
			Object  enclosedVariantAsJava = enclosedVariant.toJavaObject();
			// zero out the reference to the underlying windows memory so that
			// it is still only owned in one place by one java object 
			// (this object of type VariantVariant)
			//enclosedVariant.putEmpty(); // don't know if this would have had side effects
			if (JacobObject.isDebugEnabled()){
				JacobObject.debug("Zeroing out enclosed Variant's ref to windows memory");
			}
			enclosedVariant.m_pVariant = 0;
			return enclosedVariantAsJava;
		} else {
			throw new IllegalStateException(
					"getVariant() only legal on Variants of type VariantVariant, not "
							+ this.getvt());
		}
	}

	/**
	 * Returns the variant type via a native method call.
	 * Added 1.12 pre 6 - VT_VARIANT support is at an alpha level
	 * @return Variant one of the VT_Variant types
	 */
	private native Variant getVariantVariant();
    
    /**
     * get the content of this variant as a short
     * @return short
     */
    private native short getVariantShortRef();

    /**
     * get the content of this variant as an int
     * @return int
     * @throws IllegalStateException if variant is not of the requested type
     */
    public short getShortRef(){
    	if ((this.getvt() & VariantShort) == VariantShort &&
        		(this.getvt() & VariantByref) == VariantByref) {
        		return getVariantShortRef();
		} else {
			throw new IllegalStateException(
					"getShortRef() only legal on byRef Variants of type VariantShort, not "+this.getvt());
		}
	}
    
    /**
     * get the content of this variant as an int
     * @return int
     */
    private native int getVariantIntRef();

    /**
     * get the content of this variant as an int
     * @return int
     * @throws IllegalStateException if variant is not of the requested type
     */
    public int getIntRef(){
    	if ((this.getvt() & VariantInt) == VariantInt &&
        		(this.getvt() & VariantByref) == VariantByref) {
        		return getVariantIntRef();
		} else {
			throw new IllegalStateException(
					"getIntRef() only legal on byRef Variants of type VariantInt, not "+this.getvt());
		}
	}

    /**
     * set the content of this variant to a short (VT_I2)
     * @param in
     */
    private native void putVariantShort(short in);

    /**
     * set the content of this variant to a short (VT_I2)
     * @param in
     */
    public void putShort(short in){
    	// verify we aren't released
    	getvt();
    	putVariantShort(in);
    }
    /**
     * get the content of this variant as a short
     * @return short
     */
    private native short getVariantShort();

    /**
     * return the int value held in this variant (fails on other types?)
     * @return int
     * @throws IllegalStateException if variant is not of the requested type
     */
    public short getShort(){
    	if (this.getvt() == VariantShort){
    		return getVariantShort();
    	} else {
    		throw new IllegalStateException(
    				"getShort() only legal on Variants of type VariantShort, not "+this.getvt());
    	}
    }

    
    /**
     * get the content of this variant as a double
     * @return double
     */
    private native double getVariantDoubleRef();

    /**
     * 
     * @return returns the double value, throws exception if not a currency type
     * @throws IllegalStateException if variant is not of the requested type
     */
    public double getDoubleRef(){
    	if ((this.getvt() & VariantDouble) == VariantDouble &&
        		(this.getvt() & VariantByref) == VariantByref) {
        		return getVariantDoubleRef();
		} else {
			throw new IllegalStateException(
					"getDoubleRef() only legal on byRef Variants of type VariantDouble, not "+this.getvt());
		}
	}

    
    /**
     * get the content of this variant as a double representing a date
     * @return double
     */
    private native double getVariantDateRef();

    /**
     * 
     * @return returns the date value as a double, throws exception if not a currency type
     * @throws IllegalStateException if variant is not of the requested type
     */
    public double getDateRef(){
    	if ((this.getvt() & VariantDate) == VariantDate &&
    		(this.getvt() & VariantByref) == VariantByref) {
    		return getVariantDateRef();
    	} else {
    		throw new IllegalStateException(
    				"getDateRef() only legal on byRef Variants of type VariantDate, not "+this.getvt());
    	}
    }

    /**
     * returns the windows time contained in this Variant to a Java Date
     * should return null if this is not a date reference Variant
     * SF 959382 
     * @return java.util.Date
     */
    public Date getJavaDateRef(){
    	double windowsDate = getDateRef();
    	if (windowsDate == 0){
    		return null;
    	} else {
    		return DateUtilities.convertWindowsTimeToDate(windowsDate);
    	}
    }
    
    /**
     * get the content of this variant as a string
     * @return String
     */
    private native String getVariantStringRef();
    
    /**
     * gets the content of the veriant as a string ref
     * @return String retrieved from the COM area.
     * @throws IllegalStateException if variant is not of the requested type
     */
    public String getStringRef(){
    	if ((this.getvt() & VariantString) == VariantString &&
        		(this.getvt() & VariantByref) == VariantByref) {
        		return getVariantStringRef();
		} else {
			throw new IllegalStateException(
					"getStringRef() only legal on byRef Variants of type VariantString, not "+this.getvt());
		}
	}
   	

    /**
     * @deprecated superceded by SafeArray
     * @return never returns anything
     * @throws com.jacob.com.NotImplementedException
     */
    public Object toCharArray() {
        throw new NotImplementedException("Not implemented");
    }

    /**
     * Clear the content of this variant
     */
    public native void VariantClear();

    /**
     * @return the content of this variant as a Dispatch object (after possible conversion)
     */
    public Dispatch toDispatch(){
    	// now make the native call
    	return toVariantDispatch(); 
    }
    
    /**
     * native method used by toDispatch()
     * @return
     */
    private native Dispatch toVariantDispatch();

    /**
     * this returns null
     * @return ?? comment says null?
     */
    public native Object clone();

    /**
     * This method now correctly implements java toString() semantics
     * Attempts to return the content of this variant as a string
     * <ul>
     * <li>"not initialized" if not initialized
     * <li>"null" if VariantEmpty, 
     * <li>"null" if VariantError
     * <li>"null" if VariantNull
     * <li>the value if we know how to describe one of that type
     * <li>three question marks if can't convert
     * @return String value conversion, 
     * @throws IllegalStateException if there is no underlying windows data structure
     */
    public String toString(){
    	try {
    		// see if we are in a legal state
    		getvt();
    	} catch (IllegalStateException ise){
    		return "";
    	}
    	if (getvt() == VariantEmpty || getvt() == VariantError || getvt() == VariantNull){
    		return "null";
    	}
    	if (getvt() == VariantString){
        	return getString();
    	}
    	try {
    		Object foo = toJavaObject();
    		// rely on java objects to do the right thing
    		return foo.toString();
    	} catch (NotImplementedException nie){
    		// some types do not generate a good description yet
    		return "Description not available for type: "+getvt();
    	}
    }

    /**
     * return the int value held in this variant (fails on other types?)
     * @return int
     */
    private native int getVariantInt();

    /**
     * return the int value held in this variant if it is an int or a short.
     * Throws for other types.
     * @return int contents of the windows membory
     * @throws IllegalStateException if variant is not of the requested type
     */
    public int getInt(){
    	if (this.getvt() == VariantInt){
    		return getVariantInt();
    	} else if (this.getvt() == VariantShort){
    		return (int)getVariantShort();
    	} else {
    		throw new IllegalStateException(
    				"getInt() only legal on Variants of type VariantInt, not "+this.getvt());
    	}
    }

    /**
     * @return double return the date (as a double) value held in this variant (fails on other types?)
     */
    private native double getVariantDate();

    /**
     * @return double return the date (as a double) value held in this variant (fails on other types?)
     * @throws IllegalStateException if variant is not of the requested type
     */
    public double getDate(){
    	if (this.getvt() == VariantDate){
    		return getVariantDate();
    	} else {
    		throw new IllegalStateException(
    				"getDate() only legal on Variants of type VariantDate, not "+this.getvt());
    	}
    }

    
    /**
     * returns the windows time contained in this Variant to a Java Date.
     * should return null if this is not a date Variant
     * SF 959382 
     * @return java.util.Date returns the date if this is a VariantDate != 0,
     * 	null if it is a VariantDate == 0 and throws an IllegalStateException if this isn't 
     *  a date.
     * @throws IllegalStateException if variant is not of the requested type
     */
    public Date getJavaDate(){
    	Date returnDate = null;
    	if (getvt() == VariantDate){
    		double windowsDate = getDate();
	    	if (windowsDate != 0){
	    		returnDate = DateUtilities.convertWindowsTimeToDate(getDate());
	    	}
    	} else {
    		throw new IllegalStateException(
    				"getJavaDate() only legal on Variants of type VariantDate, not "+this.getvt());
    	}
    	return returnDate;
    }
    
    /** 
     * set the value of this variant and set the type 
     * @param in
     */
    private native void putVariantInt(int in);
    
    /** 
     * set the value of this variant and set the type 
     * @param in
     */
    public void putInt(int in){
    	// verify we aren't released yet
    	getvt();
    	putVariantInt(in);
    }

    /** 
     * set the value of this variant 
     * @param in
     */
    private native void putVariantDate(double in);

    /**
     * puts a windows date double into the variant and sets the type
     * @param in
     */
    public void putDate(double in){
    	// verify we aren't released yet
    	getvt();
    	putVariantDate(in);
    }
    /**
     * converts a java date to a windows time and calls putDate(double)
     * SF 959382 
     * @throws IllegalArgumentException if inDate = null
     * @param inDate a Java date to be converted
     */
    public void putDate(Date inDate){
    	if (inDate == null){
    		throw new IllegalArgumentException("Cannot put null in as windows date");
    		// do nothing
    	} else {
    		putDate(DateUtilities.convertDateToWindowsTime(inDate));
    	}
    }
    
    /** 
     * attempts to return the content of this variant as a double 
     * (after possible conversion)
     * @deprecated should be replaced by changeType() followed by getByte()
     * @return byte
     */
    public byte toByte(){
    	changeType(Variant.VariantByte);
    	return getByte();
    }

    /** 
     * cover for {@link #toDispatch()}
     * This method now matches other getXXX() methods.  It throws an IllegalStateException
     * if the object is not of type VariantDispatch
     * @return this object as a dispatch 
     * @throws IllegalStateException if wrong variant type
     */
    public Dispatch getDispatch() {
    	if ((this.getvt() & VariantDispatch) == VariantDispatch) {
        		return toDispatch();
		} else {
			throw new IllegalStateException(
					"getDispatch() only legal on Variants of type VariantDispatch, not "+this.getvt());
		}
   	}

    /** 
     * This acts a cover for 
     * same as @link #putObject(Object)
     * 
     * Why isn't this typed as type Dispatch?
     * @param in
     */
    public void putDispatch(Dispatch in) {
        putVariantDispatch(in);
    }

    /**
     * 
     * @return the value in this Variant as a boolean, null if not a boolean
     */
    private native boolean getVariantBoolean();

    /**
     * 
     * @return returns the value as a boolean, throws an exception if its not.
     * @throws IllegalStateException if variant is not of the requested type
     */
    public boolean getBoolean(){
    	if (this.getvt() == VariantBoolean){
    		return getVariantBoolean();
    	} else {
    		throw new IllegalStateException (
    				"getBoolean() only legal on Variants of type VariantBoolean, not "+this.getvt());
    	}
    }

    /**
     * 
     * @return the value in this Variant as a byte, null if not a byte
     */
    private native byte getVariantByte();

    /**
     * 
     * @return returns the value as a boolean, throws an exception if its not.
     * @throws IllegalStateException if variant is not of the requested type
     */
    public byte getByte(){
    	if (this.getvt() == VariantByte){
    		return getVariantByte();
    	} else {
    		throw new IllegalStateException(
    				"getByte() only legal on Variants of type VariantByte, not "+this.getvt());
    	}
    }

    /**
     * puts a boolean into the variant and sets it's type
     * @param in the new value
     */
    private native void putVariantBoolean(boolean in);
    
    /**
     * puts a boolean into the variant and sets it's type
     * @param in the new value
     */
    public void putBoolean(boolean in){
    	// verify we aren't released yet
    	getvt();
    	putVariantBoolean(in);
    }

    private native void putVariantByte(byte in);
    
    /**
     * pushes a byte into the varaint and sets the type
     * @param in
     */
    public void putByte(byte in){
    	// verify we aren't released yet
    	getvt();
    	putVariantByte(in);
    }

    /**
     * converts to an error type and returns the error
     * @deprecated should use changeType() followed by getError()
     * @return the error as an int (after conversion)
     */
    public int toError(){
    	changeType(Variant.VariantError);
    	return getError();
    }

    /**
     * Acts a a cover for toDispatch.
     * This primarily exists to support jacobgen.
     * @deprecated this is a cover for toDispatch();
     * @return Object returned by toDispatch()
     * @see Variant#toDispatch() instead
     */
    public Object toObject() {
        return toDispatch();
    }

    /**
     * Pointless method that was put here so that putEmpty() has a get method.
     * This would have returned null if the value was VT_EMPTY
     * or if it wasn't so it would have always returned the same value.
     * @deprecated method never did anything
     */
    public void getEmpty() {};

    /**
     * Sets the type to VariantEmpty.  No values needed 
     */
    private native void putVariantEmpty();
    
    /**
     * sets the type to VariantEmpty
     *
     */
    public void putEmpty(){
    	// verify we aren't released yet
    	getvt();
    	putVariantEmpty();
    }
    
    /**
     * Sets the type to VariantDispatch and sets the value to null
     * Equivalent to VB's nothing
     */
    private native void putVariantNothing();
    
    /**
     * Sets the type to VariantDispatch and sets the value to null
     * Equivalent to VB's nothing
     */
    public void putNothing(){
    	// verify we aren't released yet
    	getvt();
    	putVariantNothing();
    }

    private native int getVariantError();

    /**
     * @return double return the error value held in this variant (fails on other types?)
     * @throws IllegalStateException if variant is not of the requested type
     */
    public int getError(){
    	if (this.getvt() == VariantError){
    		return getVariantError();
    	} else {
    		throw new IllegalStateException(
    				"getError() only legal on Variants of type VariantError, not "+this.getvt());
    	}
    }

    
    private native void putVariantError(int in);
    
    /**
     * puts an error code (I think) into the variant and sets the type
     * @param in
     */
    public void putError(int in){
    	// verify we aren't released yet
    	getvt();
    	putVariantError(in);
    }

    private native double getVariantDouble();

    /**
     * @return double return the double value held in this variant (fails on other types?)
     * @throws IllegalStateException if variant is not of the requested type
     */
    public double getDouble(){
    	if (this.getvt() == VariantDouble){
    		return getVariantDouble();
    	} else {
    		throw new IllegalStateException(
    				"getDouble() only legal on Variants of type VariantDouble, not "+this.getvt());
    	}
    }

    
    private native void putVariantCurrency(long in);
    
    /**
     * puts a value in as a currency and sets the variant type
     * @param in
     */
    public void putCurrency(long in){
    	// verify we aren't released yet
    	getvt();
    	putVariantCurrency(in);
    }

    /** 
     * Puts an object into the Variant -- converts to Dispatch.
     * Acts as a cover for putVariantDispatch();
     * This primarily exists to support jacobgen.
     * This should be deprecated.
     * @see Variant#putDispatch(Dispatch) 
     * @deprecated should use putDispatch()  
     * */
    public void putObject(Object in){ 
    	// this should verify in instanceof Dispatch
    	putVariantDispatch(in); }
    
    /**
     * the JNI implementation for putDispatch() so that 
     * we can screen the incoming dispatches in putDispatch() before this
     * is invoked
     * @param in should be a dispatch object
     */
    private native void putVariantDispatch(Object in);

    private native void putVariantDouble(double in);
    
    public void putDouble(double in){
    	// verify we aren't released yet
    	getvt();
    	putVariantDouble(in);
    }

    /**
     * 
     * @return the value in this Variant as a long, null if not a long
     */
    private native long getVariantCurrency();
    
    /**
     * 
     * @return returns the currency value as a long, throws exception if not a currency type
     * @throws IllegalStateException if variant is not of the requested type
     */
    public long getCurrency(){
    	if (this.getvt() == VariantCurrency){
    		return getVariantCurrency();
    	} else {
    		throw new IllegalStateException(
    				"getCurrency() only legal on Variants of type VariantCurrency, not "+this.getvt());
    	}
    }

    private native void putVariantFloatRef(float in);
    
    /**
     * pushes a float into the variant and sets the type
     * @param in
     */
    public void putFloatRef(float in){
    	// verify we aren't released yet
    	getvt();
    	putVariantFloatRef(in);
    }

    private native void putVariantCurrencyRef(long in);
    
    /**
     * pushes a long into the variant as currency and sets the type
     * @param in
     */
    public void putCurrencyRef(long in){
    	// verify we aren't released yet
    	getvt();
    	putVariantCurrencyRef(in);
    }

    private native void putVariantErrorRef(int in);
    
    /**
     * pushes an error code into the variant by ref and sets the type
     * @param in
     */
    public void putErrorRef(int in){
    	// verify we aren't released yet
    	getvt();
    	putVariantErrorRef(in);
    }

    private native void putVariantBooleanRef(boolean in);

    /**
     * pushes a boolean into the variant by ref and sets the type of the variant to boolean
     * @param in 
     */
    public void putBooleanRef(boolean in){
    	// verify we aren't released yet
    	getvt();
    	putVariantBooleanRef(in);
    }
    
    /**
     * Just a cover for putObject().
     * We shouldn't accept any old random object.
     * This has been left in to support jacobgen.
     * This should be deprecated.
     * @param in
     * @deprecated
     */
    public void putObjectRef(Object in) {
        putObject(in);
    }

    private native void putVariantByteRef(byte in);

    /**
     * pushes a byte into the variant by ref and sets the type
     * @param in
     */
    public void putByteRef(byte in){
    	// verify we aren't released yet
    	getvt();
    	putVariantByteRef(in);
    }
    /**
     * Native method that actually extracts a string value from the variant
     * @return
     */
    private native String getVariantString();
    
    /**
     * 
     * @return string contents of the variant.
     * @throws IllegalStateException if this variant is not of type String
     */
    public String getString(){
    	if (getvt() == Variant.VariantString){
    		return getVariantString();
    	} else {
    		throw new IllegalStateException(
    				"getString() only legal on Variants of type VariantString, not "+this.getvt());
    	}
    }

    private native void putVariantString(String in);

    /**
     * put a string into the variant and set its type
     * @param in
     */
    public void putString(String in){
    	// verify we aren't released yet
    	getvt();
    	putVariantString(in);
    }
    
    private native float getVariantFloatRef();

    /**
     * 
     * @return returns the float value, throws exception if not a currency type
     * @throws IllegalStateException if variant is not of the requested type
     */
    public float getFloatRef(){
    	if ((this.getvt() & VariantFloat) == VariantFloat &&
        		(this.getvt() & VariantByref) == VariantByref) {
        		return getVariantFloatRef();
		} else {
			throw new IllegalStateException(
					"getFloatRef() only legal on byRef Variants of type VariantFloat, not "+this.getvt());
		}
	}


    private native long getVariantCurrencyRef();

    /**
     * 
     * @return returns the currency value as a long, throws exception if not a currency type
     * @throws IllegalStateException if variant is not of the requested type
     */
    public long getCurrencyRef(){
    	if ((this.getvt() & VariantCurrency) == VariantCurrency &&
        		(this.getvt() & VariantByref) == VariantByref) {
        		return getVariantCurrencyRef();
		} else {
			throw new IllegalStateException(
					"getCurrencyRef() only legal on byRef Variants of type VariantCurrency, not "+this.getvt());
		}
	}


    private native int getVariantErrorRef();

    /**
     * 
     * @return returns the error value as an int, throws exception if not a currency type
     * @throws IllegalStateException if variant is not of the requested type
     */
    public int getErrorRef(){
    	if ((this.getvt() & VariantError) == VariantError &&
        		(this.getvt() & VariantByref) == VariantByref) {
        		return getVariantErrorRef();
		} else {
			throw new IllegalStateException(
					"getErrorRef() only legal on byRef Variants of type VariantError, not "+this.getvt());
		}
	}


    private native boolean getVariantBooleanRef();
    
    /**
     * public cover for native method
     * @return the boolean from a booleanRef
     * @throws IllegalStateException if variant is not of the requested type
     */
    public boolean getBooleanRef(){
    	if ((this.getvt() & VariantBoolean) == VariantBoolean &&
        		(this.getvt() & VariantByref) == VariantByref) {
        		return getVariantBooleanRef();
		} else {
			throw new IllegalStateException(
					"getBooleanRef() only legal on byRef Variants of type VariantBoolean, not "+this.getvt());
		}
	}

    private native byte getVariantByteRef();

    /**
     * public cover for native method
     * @return the byte from a booleanRef
     * @throws IllegalStateException if variant is not of the requested type
     */
    public byte getByteRef(){
    	if ((this.getvt() & VariantByte) == VariantByte &&
        		(this.getvt() & VariantByref) == VariantByref) {
        		return getVariantByteRef();
		} else {
			throw new IllegalStateException(
					"getByteRef() only legal on byRef Variants of type VariantByte, not "+this.getvt());
		}
	}

    
    /**
     * attempts to return the contents of this variant as a float
     * (after possible conversion)
     * @deprecated should use changeType() and getFloat() instead
     * @return float
     */
    public float toFloat(){
    	changeType(Variant.VariantFloat);
    	return getFloat();
    }

    private native SafeArray toVariantSafeArray(boolean deepCopy);

    /**
     * By default toSafeArray makes a deep copy due to the fact that this
     * Variant owns the embedded SafeArray and will destroy it when it gc's
     * calls toSafeArray(true).
     */
    public SafeArray toSafeArray() {
    	// verify we haven't been released yet
    	getvt();
        return toSafeArray(true);
    }
    
    /**
     * This lets folk turn into a safe array without a deep copy.
     * Shoudl this API be public?
     * @param deepCopy
     * @return SafeArray constructed
     */
    public SafeArray toSafeArray(boolean deepCopy){
    	// verify we haven't been released yet
    	getvt();
    	return toVariantSafeArray(deepCopy);
    }

    private native void putVariantSafeArrayRef(SafeArray in);

    /**
     * have no idea...
     * @param in
     */
    public void putSafeArrayRef(SafeArray in){
    	// verify we haven't been released yet
    	getvt();
    	putVariantSafeArrayRef(in);
    }
    
    private native void putVariantSafeArray(SafeArray in);


    /**
     * have no idea...
     * @param in
     */
    public void putSafeArray(SafeArray in){
    	// verify we haven't been released yet
    	getvt();
    	putVariantSafeArray(in);
    }
    
    /**
     * sets the type to VT_ERROR and the error message to DISP_E_PARAMNOTFOIUND
     * */
    private native void putVariantNoParam();
    
    /**
     * sets the type to VT_ERROR and the error message to DISP_E_PARAMNOTFOIUND
     * */
    public void putNoParam(){
    	// verify we aren't released yet
    	getvt();
    	putVariantNoParam();
    }

    /**
     * sets the type to VT_ERROR and the error message to DISP_E_PARAMNOTFOIUND
     * @deprecated replaced by putNoParam()
     * */
    public void noParam(){
    	putNoParam();
    }
    /**
     * @deprecated superceded by SafeArray
     * @throws com.jacob.com.NotImplementedException
     */ 
    public void putCharArray(Object in) {
        throw new NotImplementedException("Not implemented");
    }

    /**
     * 
     * @return returns the value as a float if the type is of type float
     */
    private native float getVariantFloat();

    /**
     * @return returns the value as a float if the type is of type float
     * @throws IllegalStateException if variant is not of the requested type
     */
    public float getFloat(){
    	if (this.getvt() == VariantFloat){
    		return getVariantFloat();
    	} else {
    		throw new IllegalStateException(
    				"getFloat() only legal on Variants of type VariantFloat, not "+this.getvt());
    	}
    }

    
    /**
     * fills the Variant with a float and sets the type to float
     * @param in
     */
    private native void putVariantFloat(float in);

    /**
     * fills the Variant with a float and sets the type to float
     * @param in
     */
    public void putFloat(float in){
    	// verify we haven't been released yet
    	getvt();
    	putVariantFloat(in);
    }
    
    /**
     * Dispatch and dispatchRef are treated the same
     * This is a cover for putVariantDispatch().  
     * Dispatch and dispatchRef are treated the same
     * @param in
     */
    public void putDispatchRef(Dispatch in) {
        putVariantDispatch(in);
    }

    /**
     * Dispatch and dispatchRef are treated the same
     * This is just a cover for toDispatch() with a flag check
     * @return the results of toDispatch()
     * @throws IllegalStateException if variant is not of the requested type
     */
    public Dispatch getDispatchRef() {
    	if ((this.getvt() & VariantDispatch) == VariantDispatch &&
        		(this.getvt() & VariantByref) == VariantByref) {
        		return toDispatch();
		} else {
			throw new IllegalStateException(
					"getDispatchRef() only legal on byRef Variants of type VariantDispatch, not "+this.getvt());
		}
	}

    /**
     * @deprecated superceded by SafeArray
     * @throws com.jacob.com.NotImplementedException
     */ 
    public void putVariantArrayRef(Variant[] in) {
        throw new NotImplementedException("Not implemented");
    }

    /**
     * @deprecated superceded by SafeArray
     * @throws com.jacob.com.NotImplementedException
     */ 
    public Variant[] getVariantArrayRef() {
        throw new NotImplementedException("Not implemented");
    }

    /**
     * Converts variant to the passed in type by converting the underlying 
     * windows variant structure. private so folks use public java method
     * @param in the desired resulting type
     */
    private native void changeVariantType(short in);

    /**
     * Cover for native method so we can cover it.
     * <p>
     * This cannot convert an object to a byRef.
     * It can convert from byref to not byref
     * @param in type to convert this variant too
     * @return Variant returns this same object so folks can change when replacing calls
     * 	toXXX() with changeType().getXXX()
     */
    public Variant changeType(short in) {
        changeVariantType((short) in);
        return this;
    }

    /**
     * I don't know what this is.  Is it some legacy (pre 1.8) thing?
     * @deprecated
     * @return this object as a dispatch object by calling toDispatch()
     */
    public Object toScriptObject() {
        return toDispatch();
    }

    /**
     * public constructor, initializes and sets type to VariantEmpty
     */
    public Variant() {
    	this(null,false);
    }

    /**
     * Constructor that accepts a primitive rather than an object
     * @param in
     */
    public Variant(int in) {
    	this(new Integer(in));
    }

    /**
     * Constructor that accepts a primitive rather than an object
     * @param in
     */
    public Variant(double in) {
    	this(new Double(in));
    }

    /**
     * Constructor that accepts a primitive rather than an object
     * @param in
     */
    public Variant(boolean in) {
    	this(new Boolean(in));
    }

    /** 
     * Convenience constructor that calls the main one with
     * a byRef value of false
     * @param in object to be made into variant
     */
    public Variant(Object in) {
        this(in, false);
    }

    /**
     * constructor that accepts the data object and informaton about
     * whether this is by reference or not.
     * @param pValueObject a null object sets this to "empty"
     * @param fByRef
     */
    public Variant(Object pValueObject, boolean fByRef) {
        init();
        if (pValueObject == null) {
            putEmpty();
        } else if (pValueObject instanceof Integer) {
            if (fByRef)
                putIntRef(((Integer) pValueObject).intValue());
            else
                putInt(((Integer) pValueObject).intValue());
        } else if (pValueObject instanceof Short) {
            if (fByRef)
                putShortRef(((Short) pValueObject).shortValue());
            else
                putShort(((Short) pValueObject).shortValue());
        } else if (pValueObject instanceof String) {
            if (fByRef)
                putStringRef((String) pValueObject);
            else
                putString((String) pValueObject);
        } else if (pValueObject instanceof Boolean) {
            if (fByRef)
                putBooleanRef(((Boolean) pValueObject).booleanValue());
            else
                putBoolean(((Boolean) pValueObject).booleanValue());
        } else if (pValueObject instanceof Double) {
            if (fByRef)
                putDoubleRef(((Double) pValueObject).doubleValue());
            else
                putDouble(((Double) pValueObject).doubleValue());
        } else if (pValueObject instanceof Float) {
            if (fByRef)
                putFloatRef(((Float) pValueObject).floatValue());
            else
                putFloat(((Float) pValueObject).floatValue());
        }  else if (pValueObject instanceof Byte){
        	if (fByRef){
        		putByteRef(((Byte)pValueObject).byteValue());
        	} else {
        		putByte(((Byte)pValueObject).byteValue());
        	}
        } else if (pValueObject instanceof Date){
        	if (fByRef){
        		putDateRef((Date) pValueObject);
        	} else {
        		putDate((Date)pValueObject);
        	}
        } else if (pValueObject instanceof SafeArray) {
            if (fByRef)
                putSafeArrayRef((SafeArray) pValueObject);
            else
                putSafeArray((SafeArray) pValueObject);
        } else if (pValueObject instanceof Dispatch){
        	if (fByRef)
        		putDispatchRef((Dispatch)pValueObject);
        	else
        		putDispatch((Dispatch)pValueObject);
        } else if (pValueObject instanceof Variant){
        	// newly added 1.12-pre6
        	putVariant((Variant)pValueObject);
        } else {
        	// should really throw an illegal argument exception if its an invalid type
            if (fByRef)
                putObjectRef(pValueObject);
            else
                putObject(pValueObject);
        }
    }

    /**
     * Returns the variant type via a native method call
     * @return short one of the VT_xx types
     */
    private native short getVariantType();

    /**
     * Reports the type of the underlying Variant object
     * @return returns the variant type as a short, one of the Variantxxx 
     * values defined as statics in this class. returns VariantNull if not initialized
     * @throws IllegalStateException if there is no underlying windows data structure
     */
    public short getvt(){
    	if (m_pVariant != 0){
    		return getVariantType();
    	} else {
    		throw new IllegalStateException("uninitialized Variant");
    	}
    }
    
    /**
     * attempts to return the contents of this Variant as a short
     * (after possible conversion)
     * @deprecated callers should use changeType() followed by getShort()
     * @return short
     */
    public short toShort() {
    	this.changeType(Variant.VariantShort);
    	return getShort();
    }

    /**
     * now private so only this object can asccess was: call this to explicitly
     * release the com object before gc
     *  
     */
    private native void release();

    protected native void init();

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#finalize()
     */
    protected void finalize() {
    		safeRelease();
    }

    /** 
     * returns true if the passed in Variant is a constant that should not be freed
     * @param pVariant
     * @return boolian that is true if Variant is a type of constant,
     * 		VT_FALSE, VT_TRUE, VT_MISSING, DEFAULT
     */
    protected boolean objectIsAConstant(Variant pVariant){
    	if (pVariant == VT_FALSE ||
    			pVariant == VT_TRUE ||
    			pVariant == VT_MISSING ||
    			pVariant == DEFAULT){
    		return true;
    	} else {
    		return false;
    	}
    		
    }
    
    /**
     * This will release the "C" memory for the Variant 
     * unless this Variant is one of the constants in which case
     * we don't want to release the memory.
     * <p>
     * @see com.jacob.com.JacobObject#safeRelease()
     */
    public void safeRelease() {
        // The well known constants should not be released.
        // Unfortunately this doesn't fix any other classes that are
        // keeping constants around in their static ivars.
        // those will still be busted.
    	//
		// The only inconcistency here is that we leak
		// when this class is unloaded because we won't
		// free the memory even if the constants are being
		// finalized.  this is not a big deal at all.
		// another way around this would be to create the constants
		// in their own thread so that they would never be released
    	if (!objectIsAConstant(this)){
	        super.safeRelease();
	        if (m_pVariant != 0) {
	            release();
	            m_pVariant = 0;
	        } else {
	            // looks like a double release
	            // this should almost always happen due to gc
	            // after someone has called ComThread.Release
	            if (isDebugEnabled()) {
	                debug("Variant: " + this.hashCode()
	                        + " double release");
	                //Throwable x = new Throwable();
	                //x.printStackTrace();
	            }
	        }
    	} else {
            if (isDebugEnabled()) {
                debug("Variant: " + this.hashCode()
                        + " don't want to release a constant");
            }
    	}
    }

    /**
     * @deprecated superceded by SafeArray
     * @return nothing because this method is not implemented
     * @throws com.jacob.com.NotImplementedException
     */
    public Variant[] toVariantArray() {
        throw new NotImplementedException("Not implemented");
    }

    /**
     * @deprecated superceded by SafeArray
     * @return nothing because this method is not implemented
     * @throws com.jacob.com.NotImplementedException
     */
    public Object toByteArray() {
        throw new NotImplementedException("Not implemented");
    }

    /**
     *  is the variant null or empty or error or null dispatch
     * @return true if it is null or false if not
     */ 
    private native boolean isVariantConsideredNull();

    /**
     * 
     * @return returns true if the variant is considered null
     * @throws IllegalStateException if there is no underlying windows memory
     */
    public boolean isNull(){
    	getvt();
    	return isVariantConsideredNull();
    }
    
    /**
     * this is supposed to create a byte array that represents the underlying
     * variant object struct
     */
    protected native byte[] SerializationWriteToBytes();
    
    /**
     * this is supposed to cause the underlying variant object struct to 
     * be rebuilt from a previously serialized byte array.
     * @param ba
     */
    protected native void SerializationReadFromBytes(byte[] ba);
    
    /*=====================================================================
     * 
     * 
     *=====================================================================*/
    /**
	 * Convert a JACOB Variant value to a Java object 
	 * (type conversions).
     * provided in Sourceforge feature request 959381.
     * A fix was done to handle byRef bug report 1607878.
     * <p>
     * Unlike other toXXX() methods, it does not do a type conversion
     * except for special data types (it shouldn't do any!)
	 * <p>
	 * Converts Variant.VariantArray types to SafeArrays
	 * @return Corresponding Java object of the type matching the Variant type.
	 * @throws IllegalStateException if no underlying windows data structure
	 * @throws NotImplementedException if unsupported conversion is requested
	 */
	public Object toJavaObject() throws JacobException {
	    Object result = null;
	
	    short type = this.getvt(); //variant type
	
	    if ((type & Variant.VariantArray) == VariantArray) { //array returned?
		    SafeArray array = null;
		    type = (short) (type - Variant.VariantArray);
		    array = this.toSafeArray(false);
		    //result = toJava(array);
		    result = array;
	    } else { //non-array object returned
		    switch (type) {
		    case Variant.VariantEmpty : //0
		    case Variant.VariantNull : //1
		    break;
		    case Variant.VariantShort : //2
			    result = new Short(this.getShort());
			    break;
		    case Variant.VariantShort | Variant.VariantByref : //2
			    result = new Short(this.getShortRef());
			    break;
		    case Variant.VariantInt : //3
			    result = new Integer(this.getInt());
			    break;
		    case Variant.VariantInt | Variant.VariantByref: //3
			    result = new Integer(this.getIntRef());
			    break;
		    case Variant.VariantFloat : //4
			    result = new Float(this.getFloat());
			    break;
		    case Variant.VariantFloat | Variant.VariantByref: //4
			    result = new Float(this.getFloatRef());
			    break;
		    case Variant.VariantDouble : //5
			    result = new Double(this.getDouble());
			    break;
		    case Variant.VariantDouble | Variant.VariantByref: //5
			    result = new Double(this.getDoubleRef());
			    break;
		    case Variant.VariantCurrency : //6
			    result = new Long(this.getCurrency());
			    break;
		    case Variant.VariantCurrency | Variant.VariantByref: //6
			    result = new Long(this.getCurrencyRef());
			    break;
		    case Variant.VariantDate : //7
			    result = this.getJavaDate();
			    break;
		    case Variant.VariantDate | Variant.VariantByref : //7
			    result = this.getJavaDateRef();
			    break;
		    case Variant.VariantString : //8
			    result = this.getString();
			    break;
		    case Variant.VariantString | Variant.VariantByref: //8
			    result = this.getStringRef();
			    break;
		    case Variant.VariantDispatch : //9
			    result = this.getDispatch();
			    break;
		    case Variant.VariantDispatch | Variant.VariantByref: //9
			    result = this.getDispatchRef(); // Can dispatches even be byRef?
			    break;
		    case Variant.VariantError : //10
			    result = new NotImplementedException("toJavaObject() Not implemented for VariantError");
			    break;
		    case Variant.VariantBoolean : //11
			    result = new Boolean(this.getBoolean());
			    break;
		    case Variant.VariantBoolean | Variant.VariantByref: //11
			    result = new Boolean(this.getBooleanRef());
			    break;
		    case Variant.VariantVariant : //12 they are always by ref
			    result = new NotImplementedException("toJavaObject() Not implemented for VariantVariant without ByRef");
			    break;
		    case Variant.VariantVariant | Variant.VariantByref: //12
			    result = getVariant();
			    break;
		    case Variant.VariantObject : //13
			    result = new NotImplementedException("toJavaObject() Not implemented for VariantObject");
			    break;
		    case Variant.VariantByte : //17
			    result = new Byte(this.getByte());
			    break;
		    case Variant.VariantByte | Variant.VariantByref: //17
			    result = new Byte(this.getByteRef());
			    break;
		    case Variant.VariantTypeMask : //4095
			    result = new NotImplementedException("toJavaObject() Not implemented for VariantTypeMask");
			    break;
		    case Variant.VariantArray : //8192
			    result = new NotImplementedException("toJavaObject() Not implemented for VariantArray");
			    break;
		    case Variant.VariantByref : //16384
			    result = new NotImplementedException("toJavaObject() Not implemented for VariantByref");
			    break;
		    default :
			    result = new NotImplementedException("Unknown return type: " + type);
		    	// there was a "return result" here that caused defect 1602118 so it was removed  
		    	break;
	    }//switch (type)
	
	    if (result instanceof JacobException) {
	    	throw (JacobException) result;
	    }
	    }
	
	    return result;
    }//toJava()    
}