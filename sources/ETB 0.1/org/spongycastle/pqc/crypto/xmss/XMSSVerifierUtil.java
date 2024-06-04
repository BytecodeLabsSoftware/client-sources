package org.spongycastle.pqc.crypto.xmss;

import java.util.List;





class XMSSVerifierUtil
{
  XMSSVerifierUtil() {}
  
  static XMSSNode getRootNodeFromSignature(WOTSPlus wotsPlus, int height, byte[] messageDigest, XMSSReducedSignature signature, OTSHashAddress otsHashAddress, int indexLeaf)
  {
    if (messageDigest.length != wotsPlus.getParams().getDigestSize())
    {
      throw new IllegalArgumentException("size of messageDigest needs to be equal to size of digest");
    }
    if (signature == null)
    {
      throw new NullPointerException("signature == null");
    }
    if (otsHashAddress == null)
    {
      throw new NullPointerException("otsHashAddress == null");
    }
    



    LTreeAddress lTreeAddress = (LTreeAddress)((LTreeAddress.Builder)((LTreeAddress.Builder)new LTreeAddress.Builder().withLayerAddress(otsHashAddress.getLayerAddress())).withTreeAddress(otsHashAddress.getTreeAddress())).withLTreeAddress(otsHashAddress.getOTSAddress()).build();
    

    HashTreeAddress hashTreeAddress = (HashTreeAddress)((HashTreeAddress.Builder)((HashTreeAddress.Builder)new HashTreeAddress.Builder().withLayerAddress(otsHashAddress.getLayerAddress())).withTreeAddress(otsHashAddress.getTreeAddress())).withTreeIndex(otsHashAddress.getOTSAddress()).build();
    


    WOTSPlusPublicKeyParameters wotsPlusPK = wotsPlus.getPublicKeyFromSignature(messageDigest, signature
      .getWOTSPlusSignature(), otsHashAddress);
    XMSSNode[] node = new XMSSNode[2];
    node[0] = XMSSNodeUtil.lTree(wotsPlus, wotsPlusPK, lTreeAddress);
    
    for (int k = 0; k < height; k++)
    {




      hashTreeAddress = (HashTreeAddress)((HashTreeAddress.Builder)((HashTreeAddress.Builder)((HashTreeAddress.Builder)new HashTreeAddress.Builder().withLayerAddress(hashTreeAddress.getLayerAddress())).withTreeAddress(hashTreeAddress.getTreeAddress())).withTreeHeight(k).withTreeIndex(hashTreeAddress.getTreeIndex()).withKeyAndMask(hashTreeAddress.getKeyAndMask())).build();
      if (Math.floor(indexLeaf / (1 << k)) % 2.0D == 0.0D)
      {





        hashTreeAddress = (HashTreeAddress)((HashTreeAddress.Builder)((HashTreeAddress.Builder)((HashTreeAddress.Builder)new HashTreeAddress.Builder().withLayerAddress(hashTreeAddress.getLayerAddress())).withTreeAddress(hashTreeAddress.getTreeAddress())).withTreeHeight(hashTreeAddress.getTreeHeight()).withTreeIndex(hashTreeAddress.getTreeIndex() / 2).withKeyAndMask(hashTreeAddress.getKeyAndMask())).build();
        node[1] = XMSSNodeUtil.randomizeHash(wotsPlus, node[0], (XMSSNode)signature.getAuthPath().get(k), hashTreeAddress);
        node[1] = new XMSSNode(node[1].getHeight() + 1, node[1].getValue());



      }
      else
      {


        hashTreeAddress = (HashTreeAddress)((HashTreeAddress.Builder)((HashTreeAddress.Builder)((HashTreeAddress.Builder)new HashTreeAddress.Builder().withLayerAddress(hashTreeAddress.getLayerAddress())).withTreeAddress(hashTreeAddress.getTreeAddress())).withTreeHeight(hashTreeAddress.getTreeHeight()).withTreeIndex((hashTreeAddress.getTreeIndex() - 1) / 2).withKeyAndMask(hashTreeAddress.getKeyAndMask())).build();
        node[1] = XMSSNodeUtil.randomizeHash(wotsPlus, (XMSSNode)signature.getAuthPath().get(k), node[0], hashTreeAddress);
        node[1] = new XMSSNode(node[1].getHeight() + 1, node[1].getValue());
      }
      node[0] = node[1];
    }
    return node[0];
  }
}
