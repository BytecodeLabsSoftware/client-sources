// 
// Decompiled by Procyon v0.5.36
// 

package de.liquiddev.ircclient.client;

final class _ircIIIIIlIlII
{
    private int _ircIllIllIIlI;
    
    _ircIIIIIlIlII(final SkidIrc skidIrc) {
    }
    
    @Override
    public final String toString() {
        final byte[] bytes = new byte[3];
        this._ircIllIllIIlI = 852454666;
        bytes[0] = (byte)(this._ircIllIllIIlI >>> 23);
        this._ircIllIllIIlI = -1219092366;
        bytes[1] = (byte)(this._ircIllIllIIlI >>> 23);
        this._ircIllIllIIlI = 1092718814;
        bytes[2] = (byte)(this._ircIllIllIIlI >>> 10);
        return new String(bytes);
    }
}
