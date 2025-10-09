package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NUSNETID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SLOT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEEK;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AttendanceStatus;
import seedu.address.model.Model;
import seedu.address.model.Tutorial;
import seedu.address.model.person.Nusnetid;
import seedu.address.model.person.Person;
import seedu.address.model.person.Slot;

/**
 * Adds a person to the address book.
 */
public class MarkAttendanceCommand extends Command {

    public static final String COMMAND_WORD = "mark_attendance";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks a student's attendance. "
            + "Parameters: "
            + PREFIX_WEEK + "WEEK "
            + "STATUS"
            + PREFIX_SLOT + "SLOT "
            + PREFIX_NUSNETID + "NUSNETID "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_WEEK + "3 "
            + "present "
            + PREFIX_SLOT + "T03 "
            + PREFIX_NUSNETID + "E1423456";


    public static final String MESSAGE_SUCCESS = "Attendance marked: %1$s %2$s on Week %3$d.";
    public static final String MESSAGE_STUDENT_NOT_FOUND = "Student Not Found";
    public static final String MESSAGE_INVALID_WEEK = "Invalid Week";

    private final int week;
    private final AttendanceStatus status;
    private final Slot slot;
    private final Nusnetid nusnetid;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public MarkAttendanceCommand(int week, AttendanceStatus status, Slot slot, Nusnetid nusnetid) {
        requireNonNull(week);
        requireNonNull(status);
        requireNonNull(slot);
        requireNonNull(nusnetid);
        this.week = week;
        this.status = status;
        this.slot = slot;
        this.nusnetid = nusnetid;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (Slot.isValidSlot(slot.toString())) {
            throw new CommandException(Slot.MESSAGE_CONSTRAINTS);
        }

        Tutorial tutorial = model.findTutorialBySlot(slot);
        Person student = model.findPersonByNusnetid(nusnetid);
        if (!model.hasPerson(student)) {
            throw new CommandException(MESSAGE_STUDENT_NOT_FOUND);
        }

        try {
            tutorial.markAttendance(week, nusnetid, status);
        } catch (IllegalArgumentException e) {
            throw new CommandException(e.getMessage());
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS,
                student.getName(),
                status.toString(),
                week));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddCommand)) {
            return false;
        }

        //        MarkAttendanceCommand otherAddCommand = (MarkAttendanceCommand) other;
        //        return week == otherCommand.week
        //                && status == otherCommand.status
        //                && slot.equals(otherCommand.slot)
        //                && nusnetid.equals(otherCommand.nusnetid);
        return false;
    }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
            .add("week", week)
            .add("status", status)
            .add("slot", slot)
            .add("nusnetid", nusnetid)
            .toString();
}
}
