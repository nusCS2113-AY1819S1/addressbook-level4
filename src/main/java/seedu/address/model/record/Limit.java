package seedu.address.model.record;


import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/*
* Limit used to offer a function as a reminder to user about their money usage.
*
* */
public class Limit {
    private Date date_start;
    private Date date_end;
    private Money limit_money;
    private MoneyFlow Latest;

  public Limit (Date date_start ,Date date_end, Money limit_money) {
      requireAllNonNull(date_end, date_start, limit_money );
      this.date_start=date_start;
      this.date_end=date_end;
      this.limit_money=limit_money;
  }

  //public boolean isExceeded ()
  public Date getDate_start (){ return date_start;}

  public Date getDate_end (){return date_end;}

  public Money getLimit_money() { return limit_money; }
}
