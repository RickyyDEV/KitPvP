/*    */ package ridev.com.br.utils.other;
/*    */ 
/*    */ import java.util.logging.Level;
/*    */ import java.util.logging.LogRecord;
/*    */ import java.util.logging.Logger;
/*    */
/*    */ public class ModuleLogger extends Logger {
/*    */   private static Logger logger;
/*    */   
/*    */   static {
/* 14 */
    try {
        Class.forName("net.md_5.bungee.api.ProxyServer");
    } catch (ClassNotFoundException e) {
        logger = org.bukkit.Bukkit.getLogger();
    }
/*    */   }
/*    */   
/*    */   private final String prefix;
/*    */   
/*    */   public ModuleLogger(String name) {
/* 24 */     this(logger, " [" + name + "] ");
/*    */   }
/*    */   
/*    */   public ModuleLogger(Logger parent, String name) {
/* 28 */     super(name, null);
/* 29 */     setParent(parent);
/* 30 */     setLevel(Level.ALL);
/* 31 */     this.prefix = name;
/*    */   }
/*    */ 
/*    */   
/*    */   public void log(LogRecord record) {
/* 36 */     record.setMessage(this.prefix + record.getMessage());
/* 37 */     super.log(record);
/*    */   }
/*    */   
/*    */   public ModuleLogger getModule(String module) {
/* 41 */     return new ModuleLogger(getParent(), this.prefix + " [" + module + "] ");
/*    */   }
/*    */ }


/* Location:              C:\Users\Ricardo\Desktop\Rede Cold System\RedeCold BungeeCord\plugins\RiPunish.jar!\me\talondev\punish\ModuleLogger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */