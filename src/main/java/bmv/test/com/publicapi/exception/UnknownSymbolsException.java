package bmv.test.com.publicapi.exception;

public class UnknownSymbolsException extends IllegalArgumentException {

  private final String symbols;

  public UnknownSymbolsException(String symbols) {
    super(String.format("Unknown symbols: %s", symbols));
    this.symbols = symbols;
  }

  public String getSymbols() {
    return symbols;
  }
}
