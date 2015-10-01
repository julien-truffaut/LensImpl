package lensimpl.bench

import org.openjdk.jmh.annotations.{Scope, Setup, State}

import scala.util.Random

@State(Scope.Thread)
class Nested0Input {

  val r = new Random

  def genBool(): Boolean = r.nextBoolean()
  def genInt(): Int = r.nextInt()
  def genLong(): Long = r.nextLong()
  def genStr(): String = r.nextString(r.nextInt(100))

  var n0: Nested0 = _

  @Setup
  def setup(): Unit =
    n0 = Nested0(genStr(), genInt(),
      Nested1(genStr(), genInt(),
        Nested2(genStr(), genInt(),
          Nested3(genStr(), genInt(),
            Nested4(genStr(), genInt(),
              Nested5(genStr(), genInt(),
                Nested6(genStr(), genInt())
                ,genLong()), genLong()), genLong()), genLong()), genLong()), genLong())

  val NO: Nested0 = Nested0("zgdhlhmjrztahopqdkgqigi",-327920680,
    Nested1("qtpdkqfeliwdfaquuxneawgrgscxfdnexsqxwixpkwwvpshowte",1124829713,
      Nested2("tysehjufjlehezmchgritmzpgbypsjbpkasynru",-1072495572,
        Nested3("aptbtwqnielouhrabgwugtstrjbrlovywpgajhboadgmjsjmdvhcmehmdwaliaztuzvgjglkuwmengeg",918872301,
          Nested4("ejvtwdcchcuwaadcegguuerv",-1149022154,
            Nested5("vlqtfxtthbrepthcpxoxrocparbvdqdgkvbmfsrlywxdsynyeibgygaipzkmdmsmuyanruxxoirmuerc",1480324764,
              Nested6("hewgyqkkvaoheuayvzelehxwwnxzoptiqsghhhxhhdqxfrtfsntokaobauavbtifmc",726619255)
              ,-5714776854542260210L),5128304677366840630L),9010743111940277888L),-636375720643769063L),5296381550469435162L),-6070111082840969110L)
  
}
