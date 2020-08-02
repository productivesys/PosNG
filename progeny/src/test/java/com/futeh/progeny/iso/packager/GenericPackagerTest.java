package com.futeh.progeny.iso.packager;

import com.futeh.progeny.iso.IFB_BITMAP;
import com.futeh.progeny.iso.IFE_CHAR;
import com.futeh.progeny.iso.ISOFieldPackager;
import com.futeh.progeny.iso.ISOMsg;
import org.junit.jupiter.api.Test;

public class GenericPackagerTest {
    @Test
    void extended() throws Exception {
        GenericPackager packager = new GenericPackager();

        ISOFieldPackager[] packagers = new ISOFieldPackager[131];
        packagers[0] = new IFE_CHAR(4, "MESSAGE TYPE INDICATOR");
        packagers[1] = new IFB_BITMAP(16, "BIT MAP");
        packagers[2] = new IFE_CHAR(4, "Field 2");
        packagers[65] = new IFB_BITMAP(8, "BIT MAP");
        packagers[66] = new IFE_CHAR(4, "Field 66");
        packagers[130] = new IFE_CHAR(4, "Field 130");

        packager.setFieldPackager(packagers);
        packager.setExtendedBitmap(65);
        ISOMsg msg = new ISOMsg();
        msg.setPackager(packager);
        msg.setMTI("0100");
        msg.set(2, "0123");
        //
        msg.set(66, "1234");
        msg.set(130, "1234");
        byte[] bytes = msg.pack();
        ISOMsg msg2 = new ISOMsg();
        msg2.setPackager(packager);
        msg2.unpack(bytes);
    }
}