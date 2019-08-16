package sk.bsmk.batch.person;

import com.univocity.parsers.common.record.Record;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import sk.bsmk.batch.batches.RawRow;

public class PersonItemProcessor implements ItemProcessor<RawRow, Person> {

  private static final Logger log = LoggerFactory.getLogger(PersonItemProcessor.class);

  private final CsvParser csvParser;

  {
    final CsvParserSettings settings = new CsvParserSettings();
    settings.getFormat().setDelimiter(',');
    csvParser = new CsvParser(settings);
  }

  @Override
  public Person process(final RawRow rawRow) throws Exception {

    final Record record = csvParser.parseRecord(rawRow.line());

    final String firstName = record.getString(0).toUpperCase();
    final String lastName = record.getString(1).toUpperCase();
    final int points = record.getInt(2);

    final Person transformedPerson = new Person(firstName, lastName, points);

    log.info("Converting (" + record + ") into (" + transformedPerson + ")");

    return transformedPerson;
  }
}
