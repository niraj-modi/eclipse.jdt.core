/*******************************************************************************
 * Copyright (c) 2005 BEA Systems, Inc. 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    sbandow@bea.com - initial API and implementation
 *    
 *******************************************************************************/

package org.eclipse.jdt.apt.tests.annotations.mirrortest;

import com.sun.mirror.apt.AnnotationProcessor;
import com.sun.mirror.apt.AnnotationProcessorEnvironment;
import com.sun.mirror.declaration.FieldDeclaration;
import com.sun.mirror.declaration.MethodDeclaration;
import com.sun.mirror.declaration.TypeDeclaration;

public class MirrorUtilTestAnnotationProcessor implements AnnotationProcessor
{
	public static final String NO_ERRORS = "NO ERRORS";
	
	/** Used by the test harness to verify that no errors were encountered **/
	public static String ERROR = NO_ERRORS;

	public AnnotationProcessorEnvironment env;
	
	public MirrorUtilTestAnnotationProcessor(AnnotationProcessorEnvironment env)
	{
		this.env = env;
	}

	public void process()
	{
		testHidesOverrides();
	}
	
	@SuppressWarnings("unused")
	private void assertEquals(String reason, Object expected, Object actual) {
		if (expected == actual)
			return;
		if (expected != null && expected.equals(actual))
			return;
		fail("Expected " + expected + ", but saw " + actual + ". Reason: " + reason);
	}

	@SuppressWarnings("unused")
	private void assertEquals(String reason, String expected, String actual) {
		if (expected == actual)
			return;
		if (expected != null && expected.equals(actual))
			return;
		fail("Expected " + expected + ", but saw " + actual + ". Reason: " + reason);
	}
	
	@SuppressWarnings("unused")
	private void assertEquals(String reason, int expected, int actual) {
		if (expected == actual)
			return;
		fail("Expected " + expected + ", but saw " + actual + ". Reason: " + reason);
	}
	
	private void assertTrue(String reason, boolean expected) {
		if (!expected)
			fail(reason);
	}
	
	private void fail(final String reason) {
		ERROR = reason;
		throw new IllegalStateException("Failed during test: " + reason);
	}

	@SuppressWarnings("unused")
	private void testHidesOverrides()
	{
		//set the type declarations
		TypeDeclaration type_EnvTestClass = null;
		TypeDeclaration type_A = null;
		TypeDeclaration type_B = null;
		TypeDeclaration type_C = null;
		TypeDeclaration type_D = null;
		TypeDeclaration type_I = null;
		TypeDeclaration type_J = null;
		TypeDeclaration type_K = null;
		for(TypeDeclaration type : env.getTypeDeclarations())
		{
			if(type.toString().endsWith("EnvTestClass"))
				type_EnvTestClass = type;
			if(type.toString().endsWith("A"))
				type_A = type;
			if(type.toString().endsWith("B"))
				type_B = type;
			if(type.toString().endsWith("C"))
				type_C = type;
			if(type.toString().endsWith("D"))
				type_D = type;
			if(type.toString().endsWith("I"))
				type_I = type;
			if(type.toString().endsWith("J"))
				type_J = type;
			if(type.toString().endsWith("K"))
				type_K = type;
		}
		
		//set the method declarations
		MethodDeclaration method_A = type_A.getMethods().iterator().next();
		MethodDeclaration method_B = type_B.getMethods().iterator().next();
		MethodDeclaration method_C = type_C.getMethods().iterator().next();
		MethodDeclaration method_D = type_D.getMethods().iterator().next();
		MethodDeclaration method_I = type_I.getMethods().iterator().next();
		MethodDeclaration method_K = type_K.getMethods().iterator().next();
		
		//set the field declarations
		FieldDeclaration field_A = type_A.getFields().iterator().next();
		FieldDeclaration field_B = type_B.getFields().iterator().next();
		FieldDeclaration field_C = type_C.getFields().iterator().next();
		FieldDeclaration field_D = type_D.getFields().iterator().next();
		FieldDeclaration field_I = type_I.getFields().iterator().next();
		FieldDeclaration field_K = type_K.getFields().iterator().next();

		//overrides positive tests
		assertTrue("Expect B.method() to override A.method()", env.getDeclarationUtils().overrides(method_B, method_A));
		assertTrue("Expect K.method() to override I.method()", env.getDeclarationUtils().overrides(method_K, method_I));
		
		//overrides negative tests
		assertTrue("Expect B.method() to not override C.method()", !env.getDeclarationUtils().overrides(method_B, method_C));
    	assertTrue("Expect D.method(String s) to not override A.method()", !env.getDeclarationUtils().overrides(method_D, method_A));
		
		//hides positive tests
		assertTrue("Expect B.field to hide A.field", env.getDeclarationUtils().hides(field_B, field_A));
		assertTrue("Expect D.field to hide A.field", env.getDeclarationUtils().hides(field_D, field_A));
		assertTrue("Expect K.field to hide I.field", env.getDeclarationUtils().hides(field_K, field_I));
		
    	//hides negative test
		assertTrue("Expect B.field to not hide C.field", !env.getDeclarationUtils().hides(field_B, field_C));
	}
}
