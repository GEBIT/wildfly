/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2014, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.wildfly.clustering.web.infinispan.session;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.nio.file.FileSystems;
import java.security.AllPermission;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.MonthDay;
import java.time.Period;
import java.time.Year;
import java.time.YearMonth;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DecimalStyle;
import java.time.temporal.ValueRange;
import java.time.temporal.WeekFields;
import java.time.zone.ZoneOffsetTransitionRule;
import java.time.zone.ZoneOffsetTransitionRule.TimeDefinition;
import java.time.zone.ZoneRules;
import java.util.Collections;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.junit.Test;
import org.wildfly.clustering.web.annotation.Immutable;

/**
 * Unit test for {@link MutableDetector}
 *
 * @author Paul Ferraro
 */
public class MutableDetectorTestCase {

    @Test
    public void isMutable() throws MalformedURLException, UnknownHostException {
        assertTrue(MutableDetector.isMutable(new Object()));
        assertTrue(MutableDetector.isMutable(new Date()));
        assertTrue(MutableDetector.isMutable(new AtomicInteger()));
        assertTrue(MutableDetector.isMutable(new AtomicLong()));
        assertFalse(MutableDetector.isMutable(null));
        assertFalse(MutableDetector.isMutable(Collections.EMPTY_LIST));
        assertFalse(MutableDetector.isMutable(Collections.EMPTY_MAP));
        assertFalse(MutableDetector.isMutable(Collections.EMPTY_SET));
        assertFalse(MutableDetector.isMutable(Boolean.TRUE));
        assertFalse(MutableDetector.isMutable(Character.valueOf('a')));
        assertFalse(MutableDetector.isMutable(this.getClass()));
        assertFalse(MutableDetector.isMutable(Currency.getInstance(Locale.US)));
        assertFalse(MutableDetector.isMutable(Locale.getDefault()));
        assertFalse(MutableDetector.isMutable(Byte.valueOf(Integer.valueOf(1).byteValue())));
        assertFalse(MutableDetector.isMutable(Short.valueOf(Integer.valueOf(1).shortValue())));
        assertFalse(MutableDetector.isMutable(Integer.valueOf(1)));
        assertFalse(MutableDetector.isMutable(Long.valueOf(1)));
        assertFalse(MutableDetector.isMutable(Float.valueOf(1)));
        assertFalse(MutableDetector.isMutable(Double.valueOf(1)));
        assertFalse(MutableDetector.isMutable(BigInteger.valueOf(1)));
        assertFalse(MutableDetector.isMutable(BigDecimal.valueOf(1)));
        assertFalse(MutableDetector.isMutable(InetAddress.getLocalHost()));
        assertFalse(MutableDetector.isMutable(new InetSocketAddress(InetAddress.getLocalHost(), 80)));
        assertFalse(MutableDetector.isMutable(MathContext.UNLIMITED));
        assertFalse(MutableDetector.isMutable("test"));
        assertFalse(MutableDetector.isMutable(TimeZone.getDefault()));
        assertFalse(MutableDetector.isMutable(UUID.randomUUID()));
        assertFalse(MutableDetector.isMutable(TimeUnit.DAYS));
        File file = new File(System.getProperty("user.home"));
        assertFalse(MutableDetector.isMutable(file));
        assertFalse(MutableDetector.isMutable(file.toURI()));
        assertFalse(MutableDetector.isMutable(file.toURI().toURL()));
        assertFalse(MutableDetector.isMutable(FileSystems.getDefault().getRootDirectories().iterator().next()));
        assertFalse(MutableDetector.isMutable(new AllPermission()));
        assertFalse(MutableDetector.isMutable(new ImmutableObject()));

        assertFalse(MutableDetector.isMutable(DateTimeFormatter.BASIC_ISO_DATE));
        assertFalse(MutableDetector.isMutable(DecimalStyle.STANDARD));
        assertFalse(MutableDetector.isMutable(Duration.ZERO));
        assertFalse(MutableDetector.isMutable(Instant.now()));
        assertFalse(MutableDetector.isMutable(LocalDate.now()));
        assertFalse(MutableDetector.isMutable(LocalDateTime.now()));
        assertFalse(MutableDetector.isMutable(LocalTime.now()));
        assertFalse(MutableDetector.isMutable(MonthDay.now()));
        assertFalse(MutableDetector.isMutable(Period.ZERO));
        assertFalse(MutableDetector.isMutable(ValueRange.of(0L, 10L)));
        assertFalse(MutableDetector.isMutable(WeekFields.ISO));
        assertFalse(MutableDetector.isMutable(Year.now()));
        assertFalse(MutableDetector.isMutable(YearMonth.now()));
        assertFalse(MutableDetector.isMutable(ZoneOffset.UTC));
        assertFalse(MutableDetector.isMutable(ZoneRules.of(ZoneOffset.UTC).nextTransition(Instant.now())));
        assertFalse(MutableDetector.isMutable(ZoneOffsetTransitionRule.of(Month.JANUARY, 1, DayOfWeek.SUNDAY, LocalTime.MIDNIGHT, true, TimeDefinition.STANDARD, ZoneOffset.UTC, ZoneOffset.ofHours(1), ZoneOffset.ofHours(2))));
        assertFalse(MutableDetector.isMutable(ZoneRules.of(ZoneOffset.UTC)));
        assertFalse(MutableDetector.isMutable(ZonedDateTime.now()));
        assertFalse(MutableDetector.isMutable(new JCIPImmutableObject()));
    }

    @Immutable
    static class ImmutableObject {
    }

    @net.jcip.annotations.Immutable
    static class JCIPImmutableObject {
    }
}
