/* Copyright 2004, 2005, 2006 Acegi Technology Pty Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.security.access.intercept.aopalliance;

import static org.mockito.Mockito.*;

import java.lang.reflect.Method;

import junit.framework.TestCase;

import org.springframework.security.TargetObject;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.access.method.MethodSecurityMetadataSource;

/**
 * Tests {@link MethodSecurityMetadataSourceAdvisor}.
 *
 * @author Ben Alex
 */
public class MethodSecurityMetadataSourceAdvisorTests extends TestCase {
    //~ Methods ========================================================================================================

    public void testAdvisorReturnsFalseWhenMethodInvocationNotDefined() throws Exception {
        Class<TargetObject> clazz = TargetObject.class;
        Method method = clazz.getMethod("makeLowerCase", new Class[] {String.class});

        MethodSecurityMetadataSource mds = mock(MethodSecurityMetadataSource.class);
        when(mds.getAttributes(method, clazz)).thenReturn(null);
        MethodSecurityMetadataSourceAdvisor advisor = new MethodSecurityMetadataSourceAdvisor("", mds ,"");
        assertFalse(advisor.getPointcut().getMethodMatcher().matches(method, clazz));
    }

    public void testAdvisorReturnsTrueWhenMethodInvocationIsDefined()
        throws Exception {
        Class<TargetObject> clazz = TargetObject.class;
        Method method = clazz.getMethod("countLength", new Class[] {String.class});

        MethodSecurityMetadataSource mds = mock(MethodSecurityMetadataSource.class);
        when(mds.getAttributes(method, clazz)).thenReturn(SecurityConfig.createList("ROLE_A"));
        MethodSecurityMetadataSourceAdvisor advisor = new MethodSecurityMetadataSourceAdvisor("", mds ,"");
        assertTrue(advisor.getPointcut().getMethodMatcher().matches(method, clazz));
    }
}
